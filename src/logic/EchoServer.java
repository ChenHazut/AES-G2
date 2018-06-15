package logic;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
			dbh = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/aes", "root", "root");
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
		Message m=(Message)msg;
		System.out.println("heree"+m.getqueryToDo());
		System.out.println("SQL connection succeed");
		
		if (m.getClassType().equalsIgnoreCase("User"))
			userHandler(m,client,conn);
		else if(m.getClassType().equalsIgnoreCase("Teacher"))
			teacherHandler(m,client,conn);
		else if(m.getClassType().equalsIgnoreCase("Student"))
			{
				System.out.println("bla bla");
				StudentrHandler(m,client,conn);
			}
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
		else if(msg.getqueryToDo().equals("getAllExamsRelevantToTeacher"))
			getExamsByTeacher(msg,client,conn);
		else if(msg.getqueryToDo().equals("deleteExam"))
			deleteExam(msg,client,conn);
		else if(msg.getqueryToDo().equals("updadeExam"))
			editExam(msg,client,conn);
	}
	
	//student handler= handle client request about student class
		private void StudentrHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
		{
			System.out.println("***********student handler");
			if (msg.getqueryToDo().equals("getAllGradesRelevantToStusent") ) //send to client the details 												// e.g to logIn
				{
					System.out.println("1212122121121");
					GetGradeByStudentDB(msg, client, conn);
				}
			
			if (msg.getqueryToDo().equals("getAllPerformExamsRelevantToStudent") ) //send to client the details 												// e.g to logIn
			{
				System.out.println("looking for performing exam");
				getExamsByStudent(msg, client, conn);
			}
			
		}

		private void GetGradeByStudentDB(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException 
		{
			Statement stmt = (Statement) conn.createStatement();
			User StudentToSearch=(User) msg.getSentObj();
			ResultSet rs = stmt.executeQuery(
					"SELECT sr.examID , sr.grade, sr.examDate , cs.courseName "
					+ "FROM studentresultinexam AS sr , exam AS e , courseinsubject AS cs "
					+ "WHERE sr.approved=1 AND sr.studentID="+StudentToSearch.getuID() 
					+" AND sr.examID=e.examID AND e.subjectID=cs.subjectID AND e.courseID=cs.courseID");
			ArrayList<StudentInExam> tempArr=new ArrayList<StudentInExam>();//to save all the relevant exams grade of rhe student	
			HashMap<String,Integer> map=new HashMap<String,Integer>();
			while(rs.next())
			{
				StudentInExam sGrade = new StudentInExam(rs.getString("examID"),rs.getInt("grade"),rs.getTimestamp("examDate"),rs.getString("courseName"));
				tempArr.add(sGrade);
				
			}
			rs.close();
			stmt.close();
			msg.setReturnObj(tempArr);
			client.sendToClient(msg);
		}
		
		////////////////////////////////////////////////////////////////////////////////
		/////////this func return all the performance exam on the relevent student
		///////////////////////////////////////////////////////////////////////////////
		private void getExamsByStudent(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
			
			Statement stmt = (Statement) conn.createStatement();
			User StudentToSearch=(User) msg.getSentObj();
			String s= " SELECT  E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName  , EE.executingTeacherID"
					+ " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U" 
					+ " WHERE EE.examID=E.examID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID AND SC.studentID="+StudentToSearch.getuID() + " AND EE.isGroup=0 AND EE.executingTeacherID=U.userID AND E.courseID=C.courseID AND E.subjectID=S.subjectID AND EE.locked=0"
							+ " union "
							+ " SELECT E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName , EE.executingTeacherID "
		                    + " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U " 
							+ " WHERE EE.examID=E.examID AND EE.locked=0 AND EE.executingTeacherID=U.userID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID AND E.subjectID=C.subjectID AND E.courseID=C.courseID AND E.subjectID=S.subjectID AND SC.studentID=22222 AND EE.isGroup=1 AND EG.examID=EE.examID AND EG.executionId=EE.executionID AND EG.studentID="+StudentToSearch.getuID();   
		                       
		                    
			/////////////////////////////////////////////////////////////////////////
	
			ResultSet rs = stmt.executeQuery(s);  //sent the sql as stinrg
			ArrayList<Exam> tempArr=new ArrayList<Exam>();	
			
			while(rs.next())
			{
				Exam e=new Exam();
				e.setExamID(rs.getString(1));
				e.setCourse(new Course(rs.getString(8),rs.getString(9),rs.getString(12),(new Subject(rs.getString(7),rs.getString(10)))));
				e.setWasUsed(rs.getInt(3)==1);  //CHANGE THE STATUS OF THE EXAM
				e.setInstructionForTeacher(rs.getString(4));
				e.setInstructionForStudent(rs.getString(5));
				e.setDuration(rs.getInt(6));
				e.setTeacherID(rs.getString(2));    //SAVE THE ID OF THE TEACHER THAT WROTE THE EXAM
				e.setTeacherName(rs.getString(11)); //SAVE THE TEACHER THAT WROTE THE EXAM
				tempArr.add(e); //add all the exam to the arr
			}
			
			/*for(int i=0;i<tempArr.size();i++)
			{
				rs = stmt.executeQuery("SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="+tempArr.get(i).getExamID());
				HashMap<Question,Integer> map=new HashMap<Question,Integer>();
				while(rs.next())
				{
					Question q=new Question();
					q.setQuestionID(rs.getString(2));
					q.setQuestionTxt(rs.getString(5));
					String[] ans=new String[4];
					Statement stmt2 = (Statement) conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID="+q.getQuestionID());
					for(int j=0;rs2.next();j++)
					{
						ans[j]=rs2.getString(3);
					}
					rs2.close();
					stmt2.close();
					q.setAnswers(ans);
					q.setTeacherID(rs.getString(6));
					q.setTeacherName(rs.getString(11));
					q.setCorrectAnswer(rs.getInt(8));
					q.setInstruction(rs.getString(7));
					map.put(q, rs.getInt(3));
				}
			}*/
			rs.close();
			stmt.close();
			msg.setReturnObj(tempArr);
			client.sendToClient(msg);
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
		ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userID="+uToSearch.getuID());
		if(rs.next())
		{
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getInt(3)==0?"NO":"YES");
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
		ResultSet rs = stmt.executeQuery("SELECT * FROM question AS Q, questionCourse AS QC, teacherInCourse AS TC, user AS U WHERE Q.questionID=QC.questionID AND QC.subjectID=TC.subjectID AND QC.courseID=TC.courseID AND TC.teacherID="+teacherToSearch.getuID()+" AND U.userID=TC.teacherID");
		ArrayList<Question> tempArr=new ArrayList<Question>();	
		HashMap<String,Integer> map=new HashMap<String,Integer>();
		while(rs.next())
		{
			Question q=new Question();
			q.setQuestionID(rs.getString(1));
			if(!map.containsKey(q.getQuestionID()))
			{
				q.setQuestionTxt(rs.getString(2));
				q.setTeacherID(rs.getString(3));
				q.setTeacherName(rs.getString(14));
				q.setInstruction(rs.getString(4));
				q.setCorrectAnswer(rs.getInt(5));
				Statement stmt2 = (Statement) conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM answersInQuestion AS AQ WHERE AQ.questionID="+q.getQuestionID());
				String[] ans=new String[4];
				for(int i=0;rs2.next();i++)
					ans[i]=rs2.getString(3);
				q.setAnswers(ans);
				tempArr.add(q);
				map.put(q.getQuestionID(), 1);
				stmt2.close();
				rs2.close();
			}
			
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	//search in db for question with same qID and edit the requested field.
	public void editQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		Statement stmt,stmt2;
		ResultSet rs = null,rs2=null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Question q=(Question)msg.getSentObj();
			String s="SELECT * FROM question AS Q WHERE Q.questionID="+q.getQuestionID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateString(2,q.getQuestionTxt());
			rs.updateString(4,q.getInstruction());
			rs.updateInt(5, q.getCorrectAnswer());
			rs.updateRow();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2= stmt.executeQuery("SELECT * FROM answersInQuestion AS AQ WHERE AQ.questionID="+q.getQuestionID());		
			for(int i=0;rs2.next();i++)
			{
				rs2.updateString(3,q.getAnswers()[i]);
				rs2.updateRow();
			}
			rs.close();
			rs2.close();
			stmt.close();
			stmt2.close();
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
			String s="SELECT * FROM question WHERE questionID="+q.getQuestionID();
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
			String s="SELECT * FROM user WHERE userID="+user.getuID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateInt(3,user.getIsLoggedIn().equals("YES")?1:0);
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
			String s="SELECT * FROM user WHERE userID="+user.getuID();
			rs= stmt.executeQuery(s);
			rs.last();
			rs.updateInt(3,user.getIsLoggedIn().equals("YES")?1:0);
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
			String s="SELECT * FROM subject WHERE subjectID="+subjectID;
			rs= stmt.executeQuery(s);
			rs.last();
			int temp= rs.getInt(3);
			String st=Integer.toString(temp);
			temp++;
			rs.updateInt(3, temp);
			rs.updateRow();
			rs.close();
			stmt.close();
			if(temp<10)
				return subjectID+"00"+st;
			else if(temp<100)
				return subjectID+"0"+st;
			else return subjectID+st;
		} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		return null;
		
	}
	
	private void addToQuestionCourseTable(Question q,Connection conn)
	{
		Statement stmt;
		ResultSet rs = null;
	
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String s="SELECT * FROM questioncourse";
			rs= stmt.executeQuery(s);
			for(int i=0;i<q.getCourseList().size();i++)
			{
				rs.moveToInsertRow();
				rs.updateString(1,q.getQuestionID());
				rs.updateString(2,q.getQuestionID().substring(0, 2));
				rs.updateString(3,q.getCourseList().get(i).getcID());
				rs.insertRow();
			}
			
			rs.close();
			stmt.close();
			System.out.println("00000000");
		} 
			catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	private void createQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException
	{
		Statement stmt,stmt2;
		ResultSet rs = null,rs2=null;
	
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Question q=(Question)msg.getSentObj();
			String s="SELECT * FROM question";
			rs= stmt.executeQuery(s);
			rs.moveToInsertRow();
			rs.updateString(2,q.getQuestionTxt());
			q.setQuestionID(getNextQuestionIDOfSubject(q.getQuestionID(),conn));
			rs.updateString(1,q.getQuestionID());
			rs.updateString(3,q.getTeacherID());
			rs.updateString(4,q.getInstruction());
			rs.updateInt(5, q.getCorrectAnswer());
			rs.updateString(6, q.getQuestionID().substring(0, 2));
			rs.insertRow();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2= stmt.executeQuery("SELECT * FROM answersInQuestion");		
			for(int i=0;i<4;i++)
			{
				rs2.moveToInsertRow();
				rs2.updateString(1,q.getQuestionID());
				rs2.updateString(2, Integer.toString(i));
				rs2.updateString(3, q.getAnswers()[i]);
				rs2.insertRow();
			}
			System.out.println("***"+q.getCourseList().size());
			addToQuestionCourseTable(q,conn);
			rs2.close();
			rs.close();
			stmt.close();
			stmt2.close();
			msg.setReturnObj(q);
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
		ResultSet rs = stmt.executeQuery("SELECT * FROM teacherInCourse AS TC,subject AS S, courseInSubject AS C WHERE C.subjectID=TC.subjectID AND C.courseID=TC.courseID AND TC.subjectID=S.subjectID AND TC.teacherID="+teacherToSearch.getuID());
		ArrayList<Course> courseList=new ArrayList<Course>();	
		//ArrayList<String> subjectFlag=new ArrayList<String>();
		ArrayList<Subject> subjectList=new ArrayList<Subject>();
		System.out.println("here :)");
		while(rs.next())
		{
			
			Subject s=new Subject(rs.getString(1),rs.getString(5));
			courseList.add(new Course(rs.getString(2), rs.getString(9), rs.getString(3),s));
		}
		rs.close();
		msg.setReturnObj(courseList);
		client.sendToClient(msg);
	}
	
	private void getTeacherdetails(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		String tid= (String) msg.getSentObj();
		User tmpUsr = new User();	
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userID="+tid);
		if(rs.next())
		{
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getInt(3)==1?"YES":"NO");
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}
		
		rs.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);
		
	}
	
	private void getExamsByTeacher(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch=(User) msg.getSentObj();
		String s="SELECT * FROM exam AS E, teacherInCourse AS TC,courseInSubject AS C,subject AS S,user AS U WHERE E.subjectID=TC.subjectID AND E.courseID=TC.courseID AND E.subjectID=S.subjectID AND C.subjectID=E.subjectID AND C.courseID=E.courseID AND U.userID=TC.teacherID AND TC.teacherID="+teacherToSearch.getuID();
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<Exam> tempArr=new ArrayList<Exam>();	
		//HashMap<Question,Integer> map=new HashMap<Question,Integer>();
		while(rs.next())
		{
			Exam e=new Exam();
			e.setExamID(rs.getString(1));
			e.setCourse(new Course(rs.getString(8),rs.getString(14),rs.getString(2),(new Subject(rs.getString(7),rs.getString(16)))));
			e.setWasUsed(rs.getInt(3)==1);
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setTeacherID(rs.getString(2));
			e.setTeacherName(rs.getString(19));
			tempArr.add(e);
		}
		for(int i=0;i<tempArr.size();i++)
		{
			rs = stmt.executeQuery("SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="+tempArr.get(i).getExamID());
			HashMap<Question,Integer> map=new HashMap<Question,Integer>();
			while(rs.next())
			{
				Question q=new Question();
				q.setQuestionID(rs.getString(2));
				q.setQuestionTxt(rs.getString(5));
				String[] ans=new String[4];
				Statement stmt2 = (Statement) conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID="+q.getQuestionID());
				for(int j=0;rs2.next();j++)
				{
					ans[j]=rs2.getString(3);
				}
				rs2.close();
				stmt2.close();
				q.setAnswers(ans);
				q.setTeacherID(rs.getString(6));
				q.setTeacherName(rs.getString(11));
				q.setCorrectAnswer(rs.getInt(8));
				q.setInstruction(rs.getString(7));
				map.put(q, rs.getInt(3));
			}
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}
	
	private void deleteExam(Message msg, ConnectionToClient client, Connection conn) 
	{
		Statement stmt;
		ResultSet rs = null;
		try 
		{
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Exam e=(Exam)msg.getSentObj();
			String s="SELECT * FROM exam WHERE examIDID="+e.getExamID();
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
	
	//search in db for question with same qID and edit the requested field.
		public void editExam(Message msg, ConnectionToClient client, Connection conn) throws IOException
		{
			Statement stmt,stmt2;
			ResultSet rs = null,rs2=null;
			try 
			{
				stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				Exam e=(Exam)msg.getSentObj();
				String s="SELECT * FROM exam AS E WHERE E.examID="+e.getExamID();
				rs= stmt.executeQuery(s);
				rs.last();
				rs.updateString(4,e.getInstructionForTeacher());
				rs.updateString(5,e.getInstructionForStudent());
				rs.updateInt(6, e.getDuration());
				rs.updateRow();
				stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs2= stmt.executeQuery("SELECT * FROM questionInExam AS QE WHERE QE.examID="+e.getExamID());
				while(rs2.next())
				{
					rs2.deleteRow();
				}
				Iterator it = e.getQuestions().entrySet().iterator();
				rs2= stmt.executeQuery("SELECT * FROM questionInExam AS QE WHERE QE.examID="+e.getExamID());
				while(it.hasNext())
				{
					 Map.Entry pair = (Map.Entry)it.next();
					 rs2.moveToInsertRow();
					 rs2.updateString(1, e.getExamID());
					 rs2.updateString(2, ((Question)pair.getKey()).getQuestionID());
					 rs2.updateInt(3, (int)pair.getValue());
					 rs2.insertRow();
					 it.remove();
				}

				rs.close();
				rs2.close();
				stmt.close();
				stmt2.close();
				msg.setReturnObj(e);
				client.sendToClient(msg);
			} 
				catch (SQLException e) 
			{
				e.printStackTrace();
			}
				
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
