package logic;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import common.Message;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port
	 *            The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
	}

	// Instance methods ************************************************
	
	protected Connection connectToDB() {
		Connection dbh = null;
		try {
			dbh = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/aes", "root", "CQ1162");
		} catch (SQLException ex) {
			System.out.print("Sorry we had a problem, could not connect to DB server\n");
			sendToAllClients("DBConnectFail");
		}
		return dbh;
	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	
	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws SQLException, IOException
	{
		Connection conn = null;
		conn=connectToDB();
		System.out.println("SQL connection succeed");
		Message m=(Message)msg;
		if (m.getClassType().equalsIgnoreCase("User"))
			userHandler(m,client,conn);
		else if(m.getClassType().equalsIgnoreCase("Teacher"))
			teacherHandler(m,client,conn);
		conn.close();

	}
	//**************************************************************************************
	//handlers to handle different class types request of DB
	//**************************************************************************************
	
	//user handler= handle client request about User class
	private void userHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		if (msg.getqueryToDo().equals("checkIfUserExist") ) //send to client the details 												// e.g to logIn
			searchUserInDB(msg, client, conn);
		else if(msg.getqueryToDo().equals("signIn"))
			loginUser(msg,client,conn);
		else if(msg.getqueryToDo().equals("logout"))
			logoutUser(msg,client,conn);
		
	}

	//teacher handler= handle client request about Teacher class
	private void teacherHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		if(msg.getqueryToDo().equals("getAllQuestionRelevantToTeacher"))
			getQuestionsByTeacher(msg,client,conn);
		else if(msg.getqueryToDo().equals("updadeQuestion"))
			editQuestion(msg,client,conn);
		else if(msg.getqueryToDo().equals("deleteQuestion"))
			deleteQuestion(msg,client,conn);
		else if(msg.getqueryToDo().equals("createQuestion"))
			createQuestion(msg,client,conn);
		else if(msg.getqueryToDo().equals("getAllCourseRelevantToTeacher"))
			getCoursesByTeacher(msg,client,conn);
		else if(msg.getqueryToDo().equals("getTeacherDetails"))
			getTeacherdetails(msg,client,conn);
		
			
	}
	

	

	//********************************************************************************************
	//get data or change data in DB methods
	//********************************************************************************************
	//search in db for user with same userID as sentObj in msg
	private void searchUserInDB(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		User uToSearch=(User)msg.getSentObj();
		User tmpUsr = new User();	
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE uID="+uToSearch.getuID());
		if(rs.next())
		{
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getString(3));
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}
		
		rs.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);
	}
	//search in db all questions of subjects teacher in msg.sentObj teach
	private void getQuestionsByTeacher(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch=(User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery("SELECT * FROM questions AS Q,courses AS C,users AS U WHERE U.uID=Q.TeacherID AND Q.qSubject=C.sID AND C.teacherID="+teacherToSearch.getuID());
		ArrayList<Question> tempArr=new ArrayList<Question>();	
		while(rs.next())
		{
			Question q=new Question(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
			q.setTeacherName(rs.getString(16));
			tempArr.add(q);
		}
		rs.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	//search in db for question with same qID and edit the requested field.
	public void editQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Question q=(Question)msg.getSentObj();
			String s="SELECT * FROM questions WHERE QuestionID="+q.getQuestionID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateString(1,q.getQuestionTxt());
			rs.updateString(4,q.getInstruction());
			rs.updateString(5,q.getAnswers()[0]);
			rs.updateString(6,q.getAnswers()[1]);
			rs.updateString(7,q.getAnswers()[2]);
			rs.updateString(8,q.getAnswers()[3]);
			rs.updateInt(9, q.getCorrectAnswer());
			rs.updateRow();
			rs.close();
			msg.setReturnObj(q);
			client.sendToClient(msg);
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
			
	}
	
	private void deleteQuestion(Message msg, ConnectionToClient client, Connection conn) 
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Question q=(Question)msg.getSentObj();
			String s="SELECT * FROM questions WHERE QuestionID="+q.getQuestionID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.deleteRow();
			rs.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
	}
	
	private void loginUser(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			User user=(User)msg.getSentObj();
			String s="SELECT * FROM users WHERE uID="+user.getuID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateString(3,user.getIsLoggedIn());
			rs.updateRow();
			rs.close();
			msg.setReturnObj(user);
			client.sendToClient(msg);
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void logoutUser(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			User user=(User)msg.getSentObj();
			String s="SELECT * FROM users WHERE uID="+user.getuID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateString(3,user.getIsLoggedIn());
			rs.updateRow();
			rs.close();
			msg.setReturnObj(user);
			client.sendToClient(msg);
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	private String getNextQuestionIDOfSubject(String subjectID,Connection conn)
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String s="SELECT * FROM subjects WHERE sID="+subjectID;
			rs= stmt.executeQuery(s);
			if(rs.next())
			{
				int temp= rs.getInt(3);
				rs.updateInt(3, temp++);
				String st=Integer.toString(temp);
				if(temp<10)
					return subjectID+"00"+st;
				else if(temp<100)
					return subjectID+"0"+st;
				else return subjectID+st;
			}
			rs.close();
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void createQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Question q=(Question)msg.getSentObj();
			String s="SELECT * FROM questions";
			rs= stmt.executeQuery(s);
			rs.moveToInsertRow();
			rs.updateString(1,q.getQuestionTxt());
			rs.updateString(2,getNextQuestionIDOfSubject(q.getQuestionID(),conn));
			rs.updateString(3,q.getTeacherID());
			rs.updateString(4,q.getInstruction());
			rs.updateString(5,q.getAnswers()[0]);
			rs.updateString(6,q.getAnswers()[1]);
			rs.updateString(7,q.getAnswers()[2]);
			rs.updateString(8,q.getAnswers()[3]);
			rs.updateInt(9, q.getCorrectAnswer());
			rs.updateString(10, q.getQuestionID().substring(0, 2));
			rs.insertRow();
			rs.close();
			msg.setReturnObj(q);
			System.out.println("dont worry");
			client.sendToClient(msg);
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void getCoursesByTeacher(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch=(User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery("SELECT * FROM courses AS C,subjects AS S WHERE C.sID=S.sID AND C.teacherID="+teacherToSearch.getuID());
		ArrayList<Course> courseList=new ArrayList<Course>();	
		ArrayList<String> subjectFlag=new ArrayList<String>();
		ArrayList<Subject> subjectList=new ArrayList<Subject>();
		System.out.println("here :)");
		while(rs.next())
		{
			
			Subject s=new Subject(rs.getString(5),rs.getString(6));
			if(!subjectFlag.contains(s.getSubjectID()))
			{
				
				subjectList.add(s);
				subjectFlag.add(s.getSubjectID());
			}
			courseList.add(new Course(rs.getString(1), rs.getString(2), rs.getString(3),s));
		}
		rs.close();
		msg.setReturnObj(courseList);
		client.sendToClient(msg);
	}
	
	private void getTeacherdetails(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		String tid= (String) msg.getSentObj();
		User tmpUsr = new User();	
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE uID="+tid);
		if(rs.next())
		{
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getString(3));
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}
		
		rs.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);
		
	}
	
	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0]
	 *            The port number to listen on. Defaults to 5555 if no argument is
	 *            entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
// End of EchoServer class
