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

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	
	protected Connection connectToDB() {
		Connection dbh = null;
		try {
			dbh = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/prototype", "root", "CQ1162");
		} catch (SQLException ex) {
			System.out.print("Sorry we had a problem, could not connect to DB server\n");
			sendToAllClients("DBConnectFail");
		}
		return dbh;
	}
	
	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws SQLException, IOException
	{
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/prototype", "root", "CQ1162");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SQL connection succeed");
		Message m=(Message)msg;
		if (m.getClassType().equalsIgnoreCase("User"))
			userHandler(m,client,conn);
		else if(m.getClassType().equalsIgnoreCase("Teacher"))
			teacherHandler(m,client,conn);

	}

	private void teacherHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		if(msg.getqueryToDo().equals("getAllQuestionRelevantToTeacher"))
			getQuestionsByTeacher(msg,client,conn);
	}

	private void getQuestionsByTeacher(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch=(User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery("SELECT * FROM questions AS Q,courses AS C WHERE Q.qSubject=C.sID AND C.teacherID="+teacherToSearch.getuID());
		ArrayList<Question> tempArr=new ArrayList<Question>();	
		while(rs.next())
		{
			Question q=new Question(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
			tempArr.add(q);
		}
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void userHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
	{
		if (msg.getqueryToDo().equals("checkIfUserExist") ) // If we want to check if user is exist															// e.g to logIn
			searchUserInDB(msg, client, conn);
		
	}

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
			tmpUsr.setIsLoggedIn(rs.getInt(3));
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}
		
		rs.close();
		conn.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);
	}

	public void editQuestion(Question q)
	{
		Statement stmt;
		Connection conn = null;
		ResultSet rs = null;
		try 
		{
			Connection dbh = connectToDB();
			stmt = (Statement) dbh.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String s="SELECT * FROM questions WHERE QuestionID="+q.getQuestionID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateInt("correctAns", q.getCorrectAnswer());
			rs.updateRow();
			conn.close();
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
			
	}
	
	private ArrayList<Question> PutOutAllInformation(ArrayList<Question> itemFromDB) throws SQLException {
		Connection dbh = connectToDB();

		Statement st = (Statement) dbh.createStatement();

		ResultSet rs = st.executeQuery("select * from questions ");

		while (rs.next()) {
			Question itemReturnToClient = new Question(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
			itemFromDB.add(itemReturnToClient);
		}
		rs.close();
		st.close();

		try {
			dbh.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return itemFromDB;
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
