package logic;

import java.io.File;
import java.io.FileOutputStream;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import common.Message;
import common.MyFile;
import gui.QuestionInExam;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

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
	final public static String HOST = "localhost";
	private String DBName;
	private String DBPassword;
	private Boolean isDBLoggedIn = false;
	private Connection conn;
	private HashMap<User, ConnectionToClient> connectedClients;
	private ArrayList<User> connected;

	private HashMap<ExecutionDetails, ArrayList<StudentInExam>> exemanieeList;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port
	 *            The port number to connect on.
	 */
	public EchoServer(int port, String dbName, String dbPass) {
		super(port);
		this.DBName = dbName;
		this.DBPassword = dbPass;
		connectedClients = new HashMap<User, ConnectionToClient>();
		connected = new ArrayList<User>();
		exemanieeList = new HashMap<ExecutionDetails, ArrayList<StudentInExam>>();

	}

	// Instance methods ************************************************

	protected Connection connectToDB() {
		Connection dbh = null;
		try {
			dbh = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName, "root", DBPassword);
			isDBLoggedIn = true;
		} catch (SQLException ex) {
			System.out.print("Sorry we had a problem, could not connect to DB server\n");
			sendToAllClients("DBConnectFail");
			isDBLoggedIn = false;
		}
		this.conn = dbh;
		return dbh;
	}

	public Boolean checkIfDBConnects() {
		Boolean result;
		if (connectToDB() != null)
			result = true;
		else
			result = false;
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */

	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws SQLException, IOException {
		Connection conn = null;
		conn = connectToDB();
		System.out.println("SQL connection succeed");
		if (msg instanceof MyFile) {
			System.out.println("bohamian rafsody");
			uploadExamToServer(msg, client);
			return;
		}
		Message m = (Message) msg;
		if (m.getClassType().equalsIgnoreCase("User"))
			userHandler(m, client, conn);
		else if (m.getClassType().equalsIgnoreCase("Teacher"))
			teacherHandler(m, client, conn);
		else if (m.getClassType().equalsIgnoreCase("Student")) {
			System.out.println("bla bla");
			StudentrHandler(m, client, conn);
		} else if (m.getClassType().equalsIgnoreCase("Principal"))
			principalHandler(m, client, conn);
		conn.close();

	}
	// **************************************************************************************
	// handlers to handle different class types request of DB
	// **************************************************************************************

	// user handler= handle client request about User class
	private void userHandler(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		if (msg.getqueryToDo().equals("checkIfUserExist")) // send to client the details // e.g to logIn
			searchUserInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("signIn"))
			loginUser(msg, client, conn);
		else if (msg.getqueryToDo().equals("logout"))
			logoutUser(msg, client, conn);
		else if (msg.getqueryToDo().equals("getConnection"))
			getConnection(msg, client, conn);

	}

	// teacher handler= handle client request about Teacher class
	private void teacherHandler(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		if (msg.getqueryToDo().equals("getAllQuestionRelevantToTeacher"))
			getQuestionsByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("updadeQuestion"))
			editQuestion(msg, client, conn);
		else if (msg.getqueryToDo().equals("deleteQuestion"))
			deleteQuestion(msg, client, conn);
		else if (msg.getqueryToDo().equals("createQuestion"))
			createQuestion(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllCourseRelevantToTeacher"))
			getCoursesByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("getTeacherDetails"))
			getTeacherdetails(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamsRelevantToTeacher"))
			getExamsByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("deleteExam"))
			deleteExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("updadeExam"))
			editExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamsInExecutionRelevantToTeacher"))
			getExamsInExecutionByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("getStudentsInCourse"))
			getStudentsInCourse(msg, client, conn);
		else if (msg.getqueryToDo().equals("executeNewExam"))
			executeNewExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("getExamnieesOfExam"))
			getExamnieesOfExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("lockExam"))
			lockExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllLockedExamsInExecutionRelevantToTeacher"))
			getLockedExamsInExecutionByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllWrittenExamsInExecutionRelevantToTeacher"))
			getWrittenExamsInExecutionByTeacher(msg, client, conn);
		else if (msg.getqueryToDo().equals("saveExamToDB"))
			createExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("QuestionForTeacherInCourse"))
			getQuestionsForTeacherInCourse(msg, client, conn);
		else if (msg.getqueryToDo().equals("getNextExamID"))
			getNextExamID(msg, client, conn);
		else if (msg.getqueryToDo().equals("sendOverTimeRequest"))
			sendOverTimeRequest(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamReportsTeacherWrote")) { // send to client all the subjectss. //
																				// e.g to logIn
			getAllExamReportsTeacherWrote(msg, client, conn);// reut to del
			System.out.println("inside");
		}
	}

	private void principalHandler(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		if (msg.getqueryToDo().equals("getAllExmasInDB")) // send to client all the exams. // e.g to logIn
			getAllExamsInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllQuestionsInDB")) // send to client all the questions. // e.g to logIn
			getAllQuestionsInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllStudentsInDB")) // send to client all the students. // e.g to logIn
			getAllStudentsInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllTeachersInDB")) // send to client all the teachers. // e.g to logIn
			getAllTeachersInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllCoursesInDB")) // send to client all the courses. // e.g to logIn
			getAllCoursesInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllSubjectsInDB")) // send to client all the subjectss. // e.g to logIn
			getAllSubjectsInDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllOverTimeRequests")) // send to client all the subjectss. // e.g to
			getAllOverTimeRequests(msg, client, conn);
		else if (msg.getqueryToDo().equals("approveOvertime")) // send to client all the subjectss. // e.g to
			approveOvertime(msg, client, conn);
		else if (msg.getqueryToDo().equals("denyOvertime")) // send to client all the subjectss. // e.g to
			denyOvertime(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamReportsTeacherWrote")) // send to client all the subjectss. // e.g
																				// to logIn
			getAllExamReportsTeacherWrote(msg, client, conn);// reut to del
		else if (msg.getqueryToDo().equals("getAllExamsInCourseInDB")) // send to client all the subjectss. // e.g to
																		// logIn
			statisticsforCourse(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamReportsStudentPerformed")) // send to client all the subjectss. //
																					// e.g to logIn
			GetGradeByStudentDB(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllQuestionInExam")) // send to client all the subjectss. // e.g to logIn
			getAllQuestionInExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllStudentInExam")) // send to client all the subjectss. // e.g to logIn
			getAllStudentInExam(msg, client, conn);
		else if (msg.getqueryToDo().equals("getAllExamInExecution")) // send to client all the subjectss. // e.g to
																		// logIn
			getAllExamInExecution(msg, client, conn);
	}

	private void StudentrHandler(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		System.out.println("***********student handler");
		if (msg.getqueryToDo().equals("getAllGradesRelevantToStusent")) // send to client the details // e.g to logIn
		{
			System.out.println("1212122121121");
			GetGradeByStudentDB(msg, client, conn);
		}

		if (msg.getqueryToDo().equals("getAllPerformExamsRelevantToStudent")) // send to client the details // e.g to
																				// logIn
		{
			System.out.println("looking for performing exam");
			getExamsByStudent(msg, client, conn);
		}

		if (msg.getqueryToDo().equals("getTheExamForStudentToShow")) {
			System.out.println("create the approve exam to show");
			getExamsToShowByStudent(msg, client, conn);
		}
		if (msg.getqueryToDo().equals("getExamByExamID"))
			getExamByExamID(msg, client, conn);
		if (msg.getqueryToDo().equals("getTheExamToPerformComputerized")) {
			System.out.println("get the exam to do the exam computerized");
			getExamsToPerformComp(msg, client, conn);
		}
		if (msg.getqueryToDo().equals("getStudentAnswersInExam")) {
			getStudentResultInExam(msg, client, conn);
		}
		if (msg.getqueryToDo().equals("changeStudentInExamStatus")) {
			changeStudentInExamStatus(msg, client, conn);
		}
	}

	// ********************************************************************************************
	// get data or change data in DB methods
	// ********************************************************************************************
	// search in db for user with same userID as sentObj in msg
	private void getConnection(Message msg, ConnectionToClient client, Connection conn2) throws IOException {
		msg.setReturnObj(client);
		client.sendToClient(msg);

	}

	public void getExamsToPerformComp(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		ExamInExecution examTemp = (ExamInExecution) msg.getSentObj();
		/// start to create the **questions from the exam
		Statement stmt4 = (Statement) conn.createStatement();
		ResultSet rs4 = stmt4.executeQuery(
				"SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="
						+ examTemp.getExamDet().getExamID());
		HashMap<Question, Integer> map = new HashMap<Question, Integer>();
		while (rs4.next()) {
			Question q = new Question();
			q.setQuestionID(rs4.getString(2));
			q.setQuestionTxt(rs4.getString(5));
			String[] ans = new String[4];
			Statement stmt5 = (Statement) conn.createStatement();
			ResultSet rs5 = stmt5
					.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
			for (int j = 0; rs5.next(); j++) {
				ans[j] = rs5.getString(3);
			}
			rs5.close();
			stmt5.close();
			q.setAnswers(ans);
			q.setTeacherID(rs4.getString(6));
			q.setTeacherName(rs4.getString(11));
			q.setCorrectAnswer(rs4.getInt(8));
			q.setInstruction(rs4.getString(7));
			map.put(q, rs4.getInt(3));
		}
		Exam e = new Exam();
		ExamInExecution ee = new ExamInExecution();
		e.setQuestions(map);
		ee.setExamDet(e);
		examTemp.getExamDet().setQuestions(map);
		examTemp.getExamDet().getQuestionsArrayList();
		examTemp.getExamDet().setQuestions(null);
		rs4.close();
		stmt4.close();
		msg.setReturnObj(examTemp);
		client.sendToClient(msg);
	}

	private void searchUserInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		User uToSearch = (User) msg.getSentObj();
		User tmpUsr = new User();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userID=" + uToSearch.getuID());
		if (rs.next()) {
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getInt(3) == 0 ? "NO" : "YES");
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}

		rs.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);
	}

	// search in db all questions of subjects teacher in msg.sentObj teach
	private void getQuestionsByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		Statement stmt2 = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		ResultSet rs, rs2;
		rs = stmt.executeQuery(
				"SELECT * FROM question AS Q, questionCourse AS QC, teacherInCourse AS TC, user AS U WHERE Q.questionID=QC.questionID AND QC.subjectID=TC.subjectID AND QC.courseID=TC.courseID AND TC.teacherID="
						+ teacherToSearch.getuID() + " AND U.userID=Q.teacherID");
		ArrayList<Question> tempArr = new ArrayList<Question>();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (rs.next()) {
			Question q = new Question();
			q.setQuestionID(rs.getString(1));
			if (!map.containsKey(q.getQuestionID())) {
				q.setQuestionTxt(rs.getString(2));
				q.setTeacherID(rs.getString(3));
				q.setTeacherName(rs.getString(14));
				q.setInstruction(rs.getString(4));
				q.setCorrectAnswer(rs.getInt(5));
				stmt2 = (Statement) conn.createStatement();
				rs2 = stmt2.executeQuery(
						"SELECT * FROM questionCourse AS QC,courseInSubject AS CS, subject AS S WHERE QC.questionID="
								+ q.getQuestionID()
								+ " AND QC.courseID=CS.courseID AND QC.subjectID=CS.subjectID AND S.subjectID=QC.subjectID");
				while (rs2.next()) {
					q.getCourseList().add(new Course(rs2.getString(3), rs2.getString(6), teacherToSearch.getuID(),
							new Subject(rs2.getString(2), rs2.getString(8))));
				}
				stmt2 = (Statement) conn.createStatement();
				rs2 = stmt2
						.executeQuery("SELECT * FROM answersInQuestion AS AQ WHERE AQ.questionID=" + q.getQuestionID());
				String[] ans = new String[4];
				for (int i = 0; rs2.next(); i++)
					ans[i] = rs2.getString(3);
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

	// search in db for question with same qID and edit the requested field.
	public void editQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException {
		Statement stmt, stmt2;
		ResultSet rs = null, rs2 = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Question q = (Question) msg.getSentObj();
			String s = "SELECT * FROM question AS Q WHERE Q.questionID=" + q.getQuestionID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.updateString(2, q.getQuestionTxt());
			rs.updateString(4, q.getInstruction());
			rs.updateInt(5, q.getCorrectAnswer());
			rs.updateRow();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt.executeQuery("SELECT * FROM answersInQuestion AS AQ WHERE AQ.questionID=" + q.getQuestionID());
			for (int i = 0; rs2.next(); i++) {
				rs2.updateString(3, q.getAnswers()[i]);
				rs2.updateRow();
			}
			rs.close();
			rs2.close();
			stmt.close();
			stmt2.close();
			msg.setReturnObj(q);
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void deleteQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException {
		Statement stmt, stmt2;
		ResultSet rs = null, rs2 = null;
		try {
			Question q = (Question) msg.getSentObj();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt2.executeQuery("SELECT * FROM answersInQuestion AS AQ WHERE AQ.questionID=" + q.getQuestionID());
			for (int i = 0; rs2.next(); i++) {
				rs2.deleteRow();
			}
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt2.executeQuery("SELECT * FROM questioncourse AS QC WHERE QC.questionID=" + q.getQuestionID());
			for (int i = 0; rs2.next(); i++) {
				rs2.deleteRow();
			}
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String s = "SELECT * FROM question WHERE questionID=" + q.getQuestionID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.deleteRow();
			rs.close();
			msg.setReturnObj(true);
			client.sendToClient(msg);
		} catch (SQLException e) {

			msg.setReturnObj(false);
			client.sendToClient(msg);
		}
	}

	private void loginUser(Message msg, ConnectionToClient client, Connection conn) throws IOException {

		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			User user = (User) msg.getSentObj();
			String s = "SELECT * FROM user WHERE userID=" + user.getuID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.updateInt(3, user.getIsLoggedIn().equals("YES") ? 1 : 0);
			rs.updateRow();
			rs.close();
			msg.setReturnObj(user);
			connected.add(user);
			sendToAllClients(connected);
			connectedClients.put(user, client);
			// sendToAllClients(connected);
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void logoutUser(Message msg, ConnectionToClient client, Connection conn) throws IOException {

		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			User user = (User) msg.getSentObj();
			String s = "SELECT * FROM user WHERE userID=" + user.getuID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.updateInt(3, user.getIsLoggedIn().equals("YES") ? 1 : 0);
			rs.updateRow();
			rs.close();
			msg.setReturnObj(user);
			connectedClients.remove(user);
			connected.remove(user);
			sendToAllClients(connected);
			// sendToAllClients(connected);
			System.out.println("logged out");
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getNextQuestionIDOfSubject(String subjectID, Connection conn) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String s = "SELECT * FROM subject WHERE subjectID=" + subjectID;
			rs = stmt.executeQuery(s);
			rs.last();
			int temp = rs.getInt(3);
			String st = Integer.toString(temp);
			temp++;
			rs.updateInt(3, temp);
			rs.updateRow();
			rs.close();
			stmt.close();
			if (temp < 10)
				return subjectID + "00" + st;
			else if (temp < 100)
				return subjectID + "0" + st;
			else
				return subjectID + st;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	private void addToQuestionCourseTable(Question q, Connection conn) {
		Statement stmt;
		ResultSet rs = null;

		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String s = "SELECT * FROM questioncourse";
			rs = stmt.executeQuery(s);
			for (int i = 0; i < q.getCourseList().size(); i++) {
				rs.moveToInsertRow();
				rs.updateString(1, q.getQuestionID());
				rs.updateString(2, q.getQuestionID().substring(0, 2));
				rs.updateString(3, q.getCourseList().get(i).getcID());
				rs.insertRow();
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createQuestion(Message msg, ConnectionToClient client, Connection conn) throws IOException {
		Statement stmt, stmt2;
		ResultSet rs = null, rs2 = null;

		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Question q = (Question) msg.getSentObj();
			String s = "SELECT * FROM question";
			rs = stmt.executeQuery(s);
			rs.moveToInsertRow();
			rs.updateString(2, q.getQuestionTxt());
			q.setQuestionID(getNextQuestionIDOfSubject(q.getQuestionID(), conn));
			rs.updateString(1, q.getQuestionID());
			rs.updateString(3, q.getTeacherID());
			rs.updateString(4, q.getInstruction());
			rs.updateInt(5, q.getCorrectAnswer());
			rs.updateString(6, q.getQuestionID().substring(0, 2));
			rs.insertRow();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt.executeQuery("SELECT * FROM answersInQuestion");
			for (int i = 1; i <= 4; i++) {
				rs2.moveToInsertRow();
				rs2.updateString(1, q.getQuestionID());
				rs2.updateString(2, Integer.toString(i));
				rs2.updateString(3, q.getAnswers()[i - 1]);
				rs2.insertRow();
			}
			addToQuestionCourseTable(q, conn);
			rs2.close();
			rs.close();
			stmt.close();
			stmt2.close();
			msg.setReturnObj(q);
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getCoursesByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM teacherInCourse AS TC,subject AS S, courseInSubject AS C WHERE C.subjectID=TC.subjectID AND C.courseID=TC.courseID AND TC.subjectID=S.subjectID AND TC.teacherID="
						+ teacherToSearch.getuID());
		ArrayList<Course> courseList = new ArrayList<Course>();
		// ArrayList<String> subjectFlag=new ArrayList<String>();
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		while (rs.next()) {

			Subject s = new Subject(rs.getString(1), rs.getString(5));
			courseList.add(new Course(rs.getString(2), rs.getString(9), rs.getString(3), s));
		}
		rs.close();
		msg.setReturnObj(courseList);
		client.sendToClient(msg);
	}

	private void getTeacherdetails(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		String tid = (String) msg.getSentObj();
		User tmpUsr = new User();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userID=" + tid);
		if (rs.next()) {
			tmpUsr.setuID(rs.getString(1));
			tmpUsr.setuName(rs.getString(2));
			tmpUsr.setIsLoggedIn(rs.getInt(3) == 1 ? "YES" : "NO");
			tmpUsr.setPassword(rs.getString(4));
			tmpUsr.setTitle(rs.getString(5));
		}

		rs.close();
		msg.setReturnObj(tmpUsr);
		client.sendToClient(msg);

	}

	private void getExamsByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		String s = "SELECT * FROM exam AS E, teacherInCourse AS TC,courseInSubject AS C,subject AS S,user AS U "
				+ "WHERE E.subjectID=TC.subjectID AND E.courseID=TC.courseID AND E.subjectID=S.subjectID "
				+ "AND C.subjectID=E.subjectID AND C.courseID=E.courseID AND U.userID=TC.teacherID "
				+ "AND TC.teacherID=" + teacherToSearch.getuID();
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<Exam> tempArr = new ArrayList<Exam>();
		// HashMap<Question,Integer> map=new HashMap<Question,Integer>();
		while (rs.next()) {
			Exam e = new Exam();
			e.setExamID(rs.getString(1));
			e.setCourse(new Course(rs.getString(8), rs.getString(14), rs.getString(2),
					(new Subject(rs.getString(7), rs.getString(16)))));
			e.setWasUsed(rs.getInt(3) == 1);
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setTeacherID(rs.getString(2));
			e.setTeacherName(rs.getString(19));
			tempArr.add(e);
		}
		for (int i = 0; i < tempArr.size(); i++) {
			rs = stmt.executeQuery(
					"SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="
							+ tempArr.get(i).getExamID());
			HashMap<Question, Integer> map = new HashMap<Question, Integer>();
			while (rs.next()) {
				Question q = new Question();
				q.setQuestionID(rs.getString(2));
				q.setQuestionTxt(rs.getString(5));
				String[] ans = new String[4];
				Statement stmt2 = (Statement) conn.createStatement();
				ResultSet rs2 = stmt2
						.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
				for (int j = 0; rs2.next(); j++) {
					ans[j] = rs2.getString(3);
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

	private void getQuestionsForTeacherInCourse(Message msg, ConnectionToClient client, Connection conn)
			throws IOException {
		Statement stmt;
		ResultSet rs = null, rs2 = null;
		ArrayList<Question> questionArr = new ArrayList<Question>();
		try {
			stmt = (Statement) conn.createStatement();
			Course course = (Course) msg.getSentObj();
			String s = "SELECT * From question AS q WHERE q.questionID in "
					+ "(SELECT qic.questionID FROM questionincourse AS qic " + "WHERE qic.course=" + course.getcID()
					+ "AND qic.subjectID=" + course.getSubject().getsName() + ")";
			rs = stmt.executeQuery(s);
			while (rs.next()) {
				String[] answers = new String[4];
				rs2 = stmt
						.executeQuery("SELECT * FROM answersinquestion AS ans WHERE AS.questionID=" + rs.getString(1));
				int i = 0;
				while (rs2.next()) {
					answers[++i] = rs2.getString(3);
				}
				questionArr.add(new Question(rs.getString(2), rs.getString(3), rs.getString(1), answers[0], answers[1],
						answers[2], answers[3], rs.getString(4), rs.getInt(5)));
			}
			rs.close();
			rs2.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		msg.setReturnObj(questionArr);
		client.sendToClient(msg);
	}

	private void getNextExamID(Message msg, ConnectionToClient client, Connection conn) throws IOException {
		Statement stmt;
		ResultSet rs = null;

		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Course course = (Course) msg.getSentObj();
			String s = "SELECT * From courseinsubject AS cis WHERE cis.subjectID=" + course.getcID()
					+ " AND cis.courseID=" + course.getSubject().getSubjectID();
			rs = stmt.executeQuery(s);
			rs.last();
			int temp = rs.getInt(4);
			temp++;
			rs.updateInt(4, temp);
			rs.updateRow();
			rs.close();
			stmt.close();
			String str = "" + course.getSubject().getSubjectID() + course.getcID() + (temp / 10 == 0 ? "0" : "");
			str += temp;
			msg.setReturnObj(str);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		client.sendToClient(msg);
	}

	private void getExamsInExecutionByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		String s = "SELECT EE.examID,EE.executionID,C.courseName, C.courseID, C.subjectID, EE.isGroup FROM examinexecution AS EE,exam AS E,courseInSubject AS C WHERE EE.locked=0 AND EE.executingTeacherID="
				+ teacherToSearch.getuID()
				+ " AND E.examID=EE.examID AND C.courseID=E.courseID AND C.subjectID=E.subjectID";
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<ExamInExecution> tempArr = new ArrayList<ExamInExecution>();
		while (rs.next()) {
			ExamInExecution ein = new ExamInExecution();
			ein.getExamDet().setExamID(rs.getString(1));
			System.out.println("har far i'll go");
			ein.setExecutionID(rs.getInt(2));
			ein.setCourseName(rs.getString(3));
			ein.setCourseID(rs.getString(4));
			ein.setSubjectID(rs.getString(5));
			ein.setIsGroup(rs.getInt(6) == 1 ? true : false);

			tempArr.add(ein);
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	private void deleteExam(Message msg, ConnectionToClient client, Connection conn) {
		Statement stmt, stmt2;
		ResultSet rs = null, rs2 = null;
		try {
			Exam e = (Exam) msg.getSentObj();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt2.executeQuery("SELECT * FROM questionInExam AS QE WHERE QE.examID=" + e.getExamID());
			while (rs2.next()) {
				rs2.deleteRow();
			}
			rs2.close();
			stmt2.close();

			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String s = "SELECT * FROM exam WHERE examID=" + e.getExamID();

			rs = stmt.executeQuery(s);
			rs.last();
			rs.deleteRow();
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// search in db for question with same qID and edit the requested field.
	public void editExam(Message msg, ConnectionToClient client, Connection conn) throws IOException {
		Statement stmt, stmt2;
		ResultSet rs = null, rs2 = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Exam e = (Exam) msg.getSentObj();
			String s = "SELECT * FROM exam AS E WHERE E.examID=" + e.getExamID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.updateString(4, e.getInstructionForTeacher());
			rs.updateString(5, e.getInstructionForStudent());
			rs.updateInt(6, e.getDuration());
			rs.updateRow();
			stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = stmt.executeQuery("SELECT * FROM questionInExam AS QE WHERE QE.examID=" + e.getExamID());
			while (rs2.next()) {
				rs2.deleteRow();
			}
			Iterator<Entry<Question, Integer>> it = e.getQuestions().entrySet().iterator();
			rs2 = stmt.executeQuery("SELECT * FROM questionInExam AS QE WHERE QE.examID=" + e.getExamID());
			while (it.hasNext()) {
				Map.Entry<Question, Integer> pair = (Map.Entry<Question, Integer>) it.next();
				rs2.moveToInsertRow();
				rs2.updateString(1, e.getExamID());
				rs2.updateString(2, ((Question) pair.getKey()).getQuestionID());
				rs2.updateInt(3, (int) pair.getValue());
				rs2.insertRow();
				it.remove();
			}

			rs.close();
			rs2.close();
			stmt.close();
			stmt2.close();
			msg.setReturnObj(e);
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createExam(Message msg, ConnectionToClient client, Connection conn) {
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			Exam e = (Exam) msg.getSentObj();
			stmt.executeUpdate("INSERT INTO exam (examID, teacherID, used, teacherInstruction, studentInstruction, "
					+ "duration, subjectID, courseID) values (\'" + e.getExamID() + "\', \'" + e.getTeacherID() + "\', "
					+ e.getWasUsed() + ", \'" + e.getInstructionForTeacher() + "\', \'" + e.getInstructionForStudent()
					+ "\', " + e.getDuration() + ", \'" + e.getCourse().getSubject().getSubjectID() + "\', \'"
					+ e.getCourse().getcID() + "\')");

			HashMap<Question, Integer> questions = e.getQuestions();
			for (Map.Entry<Question, Integer> entry : questions.entrySet()) {
				String s = "INSERT INTO questioninexam (examID, questionID, pointsPerQuestion) values (";
				s += "\'" + e.getExamID() + "\', \'" + entry.getKey().getQuestionID() + "\', " + entry.getValue() + ")";
				stmt.executeUpdate(s);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getStudentsInCourse(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Course c = (Course) msg.getSentObj();
		ArrayList<User> tmpUsrArr = new ArrayList<User>();
		Statement stmt = (Statement) conn.createStatement();
		System.out.println("subject: " + c.getSubject().getSubjectID() + " course:" + c.getcID());
		ResultSet rs = stmt
				.executeQuery("SELECT U.userID,U.userName FROM studentincourse AS SC,user AS U WHERE SC.subjectID="
						+ c.getSubject().getSubjectID() + " AND SC.courseID=" + c.getcID()
						+ " AND U.userID=SC.studentID");
		while (rs.next()) {
			User u = new User();
			u.setuID(rs.getString(1));
			u.setuName(rs.getString(2));
			tmpUsrArr.add(u);
		}

		rs.close();
		msg.setReturnObj(tmpUsrArr);
		client.sendToClient(msg);

	}

	private void executeNewExam(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		ExamInExecution exam = (ExamInExecution) msg.getSentObj();
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM examInExecution");
		rs.moveToInsertRow();
		rs.updateString(1, exam.getExamDet().getExamID());
		rs.updateString(3, exam.getExamCode());
		rs.updateString(4, exam.getExecTeacher().getuID());
		rs.updateInt(5, exam.isLocked() ? 1 : 0);
		rs.updateInt(11, exam.isGroup() ? 1 : 0);
		rs.insertRow();
		rs = stmt.executeQuery("SELECT * FROM examInExecution AS E WHERE E.examID=" + exam.getExamDet().getExamID());
		rs.last();
		exam.setExecutionID(rs.getInt(2));
		rs = stmt.executeQuery("SELECT * FROM examnieegroup");
		for (int i = 0; i < exam.getStudents().size(); i++) {
			rs.moveToInsertRow();
			rs.updateString(1, exam.getExamDet().getExamID());
			rs.updateInt(2, exam.getExecutionID());
			rs.updateString(3, exam.getStudents().get(i).getStudentID());
			rs.insertRow();
		}
		rs = stmt.executeQuery("SELECT * FROM exam WHERE examID=" + exam.getExamDet().getExamID());
		rs.last();
		int temp = rs.getInt(3);
		if (temp == 0) {
			rs.updateInt(3, 1);
			rs.updateRow();
		}
		rs.close();
		msg.setReturnObj(exam);
		client.sendToClient(msg);
	}

	private void getExamnieesOfExam(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		ExamInExecution exam = (ExamInExecution) msg.getSentObj();
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM examnieegroup as E, user AS U WHERE E.examID=" + exam.getExamDet().getExamID()
						+ " AND E.executionID=" + exam.getExecutionID() + " AND U.userID=E.studentID");
		ArrayList<StudentInExam> sList = new ArrayList<StudentInExam>();
		while (rs.next()) {

			StudentInExam student = new StudentInExam();
			student.setExamID(exam.getExamDet().getExamID());
			student.setExecutionID(rs.getInt(2));
			student.setStudentID(rs.getString(3));
			student.setStudentName(rs.getString(6));
			student.setStudentStatus(rs.getString(4));
			sList.add(student);
		}
		rs.close();
		msg.setReturnObj(sList);
		client.sendToClient(msg);
	}

	private void lockExam(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException {
		ExamInExecution exam;
		ExecutionDetails ed;
		if (msg.getSentObj() instanceof ExamInExecution) {
			exam = (ExamInExecution) msg.getSentObj();
			ed = new ExecutionDetails(exam.getExamDet().getExamID(), exam.getExecutionID());
		} else
			ed = (ExecutionDetails) msg.getSentObj();
		Iterator it = this.exemanieeList.entrySet().iterator();
		ArrayList<StudentInExam> students = new ArrayList<StudentInExam>();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (ed.equals(pair.getKey())) {
				students = ((ArrayList) pair.getValue());
				break;
			}
		}
		for (StudentInExam s : students) {
			System.out.println("size of students array: " + students.size());
			Iterator it2 = this.connectedClients.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry pair = (Map.Entry) it2.next();
				System.out.println("in map: " + (User) pair.getKey());
				User u = new User();
				u.setuID(s.getStudentID());
				u.setuName(s.getStudentName());
				System.out.println("user: " + u);
				if (u.equals(pair.getKey())) {
					System.out.println("found it");
					Message m = new Message();
					m.setReturnObj("examIsLocked");
					((ConnectionToClient) pair.getValue()).sendToClient(m);
					System.out.println("locked sent to students");
					break;
				}
			}
		}
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		System.out.println("examID= " + ed.getExamID());
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM examInExecution as EE WHERE EE.examID=" + ed.getExamID() + " AND EE.executionID=" + ed);
		rs.last();
		rs.updateInt(5, 1);
		rs.updateRow();
		Boolean isGroup = rs.getBoolean(11);
		rs.close();
		checkLockedExam(ed, isGroup);

	}

	private void getLockedExamsInExecutionByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		String s = "SELECT EE.examID,EE.executionID,C.courseName, C.courseID, C.subjectID, EE.isGroup FROM examinexecution AS EE,exam AS E,courseInSubject AS C WHERE EE.locked=1 AND EE.executingTeacherID="
				+ teacherToSearch.getuID()
				+ " AND E.examID=EE.examID AND C.courseID=E.courseID AND C.subjectID=E.subjectID";
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<ExamInExecution> tempArr = new ArrayList<ExamInExecution>();
		while (rs.next()) {
			ExamInExecution ein = new ExamInExecution();
			ein.getExamDet().setExamID(rs.getString(1));
			System.out.println("har far i'll go");
			ein.setExecutionID(rs.getInt(2));
			ein.setCourseName(rs.getString(3));
			ein.setCourseID(rs.getString(4));
			ein.setSubjectID(rs.getString(5));
			ein.setIsGroup(rs.getInt(6) == 1 ? true : false);
			tempArr.add(ein);
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	private void getWrittenExamsInExecutionByTeacher(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj();
		String s = "SELECT EE.examID,EE.executionID,C.courseName, C.courseID, C.subjectID, EE.isGroup, EE.executingTeacherID, U.userName FROM examinexecution AS EE,exam AS E,courseInSubject AS C , user AS U WHERE EE.examID=E.examID AND E.teacherID="
				+ teacherToSearch.getuID()
				+ " AND E.courseID=C.courseID AND E.subjectID=C.subjectID AND EE.executingTeacherID=U.userID";
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<ExamInExecution> tempArr = new ArrayList<ExamInExecution>();
		while (rs.next()) {
			ExamInExecution ein = new ExamInExecution();
			ein.getExamDet().setExamID(rs.getString(1));
			ein.setExecutionID(rs.getInt(2));
			ein.setCourseName(rs.getString(3));
			ein.setCourseID(rs.getString(4));
			ein.setSubjectID(rs.getString(5));
			ein.setIsGroup(rs.getInt(6) == 1 ? true : false);
			User u = new User();
			u.setuID(rs.getString(7));
			u.setuName(rs.getString(8));
			ein.setExecTeacher(u);
			tempArr.add(ein);
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	// This method return all the students in the data base.
	private void getAllStudentsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT *" + " FROM aes.User AS U " + " WHERE U.title = \"Student\" ";
		ArrayList<User> tempArr = new ArrayList<User>();
		ResultSet rs = stmt.executeQuery(s);
		// נעבור על כל סטודנט
		while (rs.next()) {
			User u = new User();
			u.setuID(rs.getString(1));
			u.setuName(rs.getString(2));
			tempArr.add(u);
		}
		// From here we returns the details:
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	// This method return all the teachers in the data base.
	private void getAllTeachersInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT U.userID, U.userName" + " FROM aes.User AS U " + " WHERE U.title = \"Teacher\" ";
		ArrayList<User> tempArr = new ArrayList<User>();
		ResultSet rs = stmt.executeQuery(s);
		// We updates every teacher
		while (rs.next()) {
			User u = new User();
			u.setuID(rs.getString(1));
			u.setuName(rs.getString(2));
			tempArr.add(u);
		}
		// From here we returns the details:
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void getExamByExamID(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		System.out.println("here :) fun liii");
		Statement stmt = (Statement) conn.createStatement();
		Exam exam = (Exam) msg.getSentObj();
		String s = "SELECT * FROM exam AS E, teacherInCourse AS TC,courseInSubject AS C,subject AS S,user AS U WHERE E.examID="
				+ exam.getExamID()
				+ " AND E.subjectID=TC.subjectID AND E.courseID=TC.courseID AND E.subjectID=S.subjectID AND C.subjectID=E.subjectID AND C.courseID=E.courseID AND U.userID=TC.teacherID AND U.userID=E.teacherID";
		ResultSet rs = stmt.executeQuery(s);
		// HashMap<Question,Integer> map=new HashMap<Question,Integer>();
		Exam e = new Exam();
		while (rs.next()) {

			e.setExamID(rs.getString(1));
			e.setCourse(new Course(rs.getString(8), rs.getString(14), rs.getString(2),
					(new Subject(rs.getString(7), rs.getString(17)))));
			e.setWasUsed(rs.getInt(3) == 1);
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setTeacherID(rs.getString(2));
			e.setTeacherName(rs.getString(20));
		}

		rs = stmt.executeQuery(
				"SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="
						+ e.getExamID());
		HashMap<Question, Integer> map = new HashMap<Question, Integer>();
		while (rs.next()) {
			Question q = new Question();
			q.setQuestionID(rs.getString(2));
			q.setQuestionTxt(rs.getString(5));
			String[] ans = new String[4];
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
			for (int j = 0; rs2.next(); j++) {
				ans[j] = rs2.getString(3);
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
		e.setQuestions(map);
		// System.out.println("send the exam to client");
		rs.close();
		stmt.close();
		msg.setReturnObj(e);
		client.sendToClient(msg);
	}

	private void getExamsToShowByStudent(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		StudentInExam StudentToSearch = (StudentInExam) msg.getSentObj();
		ResultSet rs = stmt.executeQuery(" SELECT  * "
				+ " FROM examinexecution as EE, exam AS E , user AS U, studentresultinexam AS SR , examnieegroup AS EG "
				+ " WHERE SR.approved=1 AND SR.studentID=" + StudentToSearch.getStudentID() + " AND SR.grade="
				+ StudentToSearch.getGrade() + " AND SR.examID=" + StudentToSearch.getExamID()
				+ " AND SR.executionID=EE.executionID AND EE.examID=" + StudentToSearch.getExamID() + " AND E.examID="
				+ StudentToSearch.getExamID()
				+ " AND EE.isGroup=0 AND EE.executingTeacherID=U.userID AND SR.computerized=1 " + " union "
				+ " SELECT  *"
				+ " FROM examinexecution as EE, exam AS E , user AS U, studentresultinexam AS SR , examnieegroup AS EG "
				+ " WHERE SR.approved=1 AND SR.studentID=" + StudentToSearch.getStudentID() + " AND SR.grade="
				+ StudentToSearch.getGrade() + " AND SR.examID=" + StudentToSearch.getExamID()
				+ " AND SR.executionID=EE.executionID AND EE.examID=" + StudentToSearch.getExamID() + " AND E.examID="
				+ StudentToSearch.getExamID() + " AND EE.isGroup=1 AND EG.examID=" + StudentToSearch.getExamID()
				+ " AND EG.executionId=SR.executionID AND EG.studentID=" + StudentToSearch.getStudentID()
				+ " AND EE.executingTeacherID=U.userID AND SR.computerized=1 ");
		///////////////////////////////////////////// NOT FINISHED
		ExamInExecution tempExam = new ExamInExecution();// to save all the relevant exams grade of rhe student
		rs.next();
		tempExam.setExecutionID(rs.getInt(2)); // save the executionID
		tempExam.setCourseID(rs.getString(19)); // save the exam of the course
		tempExam.setIsGroup(rs.getBoolean(11)); // save if the exam is for group-1 or not-0
		tempExam.setExamCode(rs.getString(3)); // save the exam code
		tempExam.setSubjectID(rs.getString(18)); // save the exam subject
		tempExam.setLocked(rs.getBoolean(5)); // save if the exam is locked
		/////////// until here finish ExamInExecution

		Exam tExam = new Exam();
		tExam.setInstructionForTeacher(rs.getString(14));
		tExam.setInstructionForStudent(rs.getString(15));
		tExam.setDuration(rs.getInt(30)); // save the actual exam duration
		tExam.setTeacherID(rs.getString(13));

		//// start to create the **subject**
		Statement stmt3 = (Statement) conn.createStatement();
		ResultSet rs3 = stmt3.executeQuery(" SELECT * FROM subject AS s WHERE s.subjectID=" + tempExam.getSubjectID());
		rs3.next();
		Subject tempSubject = new Subject(rs3.getString(1), rs3.getString(2));
		rs3.next();
		stmt3.close();
		/// finish create the subject we need

		/// start to create the **user**
		User teacherExecExam = new User(rs.getString(20), rs.getString(23));
		teacherExecExam.setuName(rs.getString(21));
		teacherExecExam.setTitle(rs.getString(24));
		tempExam.setExecTeacher(teacherExecExam); // insert the user to the exam
		/// finish create user

		//// start to create the **course**
		Statement stmt2 = (Statement) conn.createStatement();
		ResultSet rs2 = stmt2.executeQuery(" SELECT * FROM courseinsubject AS c WHERE c.subjectID="
				+ tempExam.getSubjectID() + " AND c.courseID=" + tempExam.getCourseID());
		rs2.next();
		Course tempCourse = new Course(rs2.getString(2), rs2.getString(3), tempExam.getExecTeacher().getuID(),
				tempSubject);
		rs2.close();
		stmt2.close();
		//// finish create the course
		tempExam.setCourseName(tempCourse.getcName());

		/// start to create the **questions from the exam
		Statement stmt4 = (Statement) conn.createStatement();
		ResultSet rs4 = stmt4.executeQuery(
				"SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="
						+ rs.getString(1));
		HashMap<Question, Integer> map = new HashMap<Question, Integer>();
		while (rs4.next()) {
			Question q = new Question();
			q.setQuestionID(rs4.getString(2));
			q.setQuestionTxt(rs4.getString(5));
			String[] ans = new String[4];
			Statement stmt5 = (Statement) conn.createStatement();
			ResultSet rs5 = stmt5
					.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
			for (int j = 0; rs5.next(); j++) {
				ans[j] = rs5.getString(3);
			}
			rs5.close();
			stmt5.close();
			q.setAnswers(ans);
			q.setTeacherID(rs4.getString(6));
			q.setTeacherName(rs4.getString(11));
			q.setCorrectAnswer(rs4.getInt(8));
			q.setInstruction(rs4.getString(7));
			map.put(q, rs4.getInt(3));
		}
		rs4.close();
		stmt4.close();
		/// finish to get questions
		System.out.println("num5");

		/// start to create the exam
		tExam.setQuestions(map);

		// to get the teacher who wrote the exam name
		Statement stmt6 = (Statement) conn.createStatement();
		ResultSet rs6 = stmt.executeQuery(" SELECT * FROM user AS u WHERE u.userID=" + tExam.getTeacherID());
		rs6.next();
		tExam.setTeacherName(rs6.getString(2));
		rs6.close();
		stmt6.close();
		// finish to get the teacher name

		tExam.setExamID(StudentToSearch.getExamID()); // save the examID
		/// tExam.setInstructionForTeacher(rs.getString(14));
		/// tExam.setInstructionForStudent(rs.getString(15));
		/// tExam.setDuration(rs.getShort(16)); //save the exam duration
		tExam.setCourse(tempCourse);
		tExam.setCourseName(tExam.getCourse().getcName());
		tempExam.setExamDet(tExam);
		//

		rs.close();
		stmt.close();
		msg.setReturnObj(tempExam);
		client.sendToClient(msg);
	}

	////////////////////////////////////////////////////////////////////////////////
	///////// this func return all the performance exam on the relevent student
	///////////////////////////////////////////////////////////////////////////////
	private void getExamsByStudent(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		Statement stmt = (Statement) conn.createStatement();
		User StudentToSearch = (User) msg.getSentObj();
		String s = " SELECT  E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName  , EE.executingTeacherID , EE.executionID , EE.locked , EE.examcode ,EE.isGroup"
				+ " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U"
				+ " WHERE EE.examID=E.examID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID AND SC.studentID="
				+ StudentToSearch.getuID()
				+ " AND EE.isGroup=0 AND EE.executingTeacherID=U.userID AND E.courseID=C.courseID AND E.subjectID=C.subjectID AND E.subjectID=S.subjectID AND EE.locked=0"
				+ " union "
				+ " SELECT E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName , EE.executingTeacherID , EE.executionID , EE.locked , EE.examcode ,EE.isGroup "
				+ " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U "
				+ " WHERE EE.examID=E.examID AND EE.locked=0 AND EE.executingTeacherID=U.userID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID AND E.subjectID=C.subjectID AND E.courseID=C.courseID AND E.subjectID=S.subjectID AND SC.studentID="
				+ StudentToSearch.getuID()
				+ " AND EE.isGroup=1 AND EG.examID=EE.examID AND EG.executionId=EE.executionID AND EG.studentID="
				+ StudentToSearch.getuID();

		ResultSet rs = stmt.executeQuery(s); // sent the sql as stinrg
		ArrayList<ExamInExecution> tempArr = new ArrayList<ExamInExecution>();

		while (rs.next()) {
			ExamInExecution ee = new ExamInExecution(); // create the new ExamInExecution
			Exam e = new Exam(); // create the new exam that execute

			e.setExamID(rs.getString(1));
			e.setTeacherID(rs.getString(2)); // SAVE THE ID OF THE TEACHER THAT WROTE THE EXAM
			e.setWasUsed(rs.getInt(3) == 1); // CHANGE THE STATUS OF THE EXAM
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setCourse(new Course(rs.getString(8), rs.getString(9), rs.getString(2),
					(new Subject(rs.getString(7), rs.getString(10)))));

			String tempID = new String(rs.getString(2)); //
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(" SELECT U.userName FROM user AS U WHERE U.userID =" + tempID);
			rs2.next();
			e.setTeacherName(rs2.getString(1));

			// ee.setExamDet(e);
			ee.setExecutionID(rs.getInt(13));
			ee.setLocked(rs.getBoolean(14));
			ee.setExamCode(rs.getString(15));
			ee.setIsGroup(rs.getBoolean(16));
			ee.setSubjectID(rs.getString(7));
			ee.setCourseID(rs.getString(8));
			ee.setCourseName(rs.getString(9));
			// finish to create the exam
			User u = new User();
			u.setuID(rs.getString(12));
			u.setuName(rs.getString(11));
			ee.setExecTeacher(u);

			// finish to create the exam to execute

			HashMap<Question, Integer> questionsInExam = new HashMap<Question, Integer>();
			rs2 = stmt2.executeQuery("SELECT * FROM question as q, questioninexam as qic"
					+ " WHERE qic.questionID=q.questionID AND qic.examID=" + e.getExamID());

			while (rs2.next()) {
				String temp = rs2.getString(1);
				Statement stmt3 = (Statement) conn.createStatement();
				String str = "SELECT * FROM answersinquestion AS ans WHERE ans.questionID=" + temp;
				ResultSet rs3 = stmt3.executeQuery(str);
				String[] answers = new String[4];
				int i = 0;
				while (rs3.next()) {
					answers[i++] = rs3.getString(3);
				}
				rs3.close();
				Question q = new Question(rs2.getString(2), rs2.getString(1), rs2.getString(3), rs.getString(4),
						answers[0], answers[1], answers[2], answers[3], rs2.getInt(5));
				questionsInExam.put(q, rs2.getInt(9));
			}
			e.setQuestions(questionsInExam);

			ee.setExamDet(e);
			tempArr.add(ee); // add all the exam in exacution to the arr
			rs2.close();
			stmt2.close();
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void uploadExamToServer(Object msg, ConnectionToClient client) {
		int fileSize = ((MyFile) msg).getSize();
		MyFile myF = (MyFile) msg;
		try {

			File newFile = new File("C:\\exams\\submittedExams\\SERVER_" + myF.getFileName() + ".docx");
			FileOutputStream out = new FileOutputStream(newFile);
			out.write(myF.getMybytearray());
			out.close();
		} catch (Exception e) {

		}
	}

	private void checkLockedExam(ExecutionDetails ed, Boolean isGroup1) throws SQLException {
		Thread checkExamThread;
		checkExamThread = new Thread(new Runnable() {
			@Override
			public void run() {
				CheckExam ce = new CheckExam();
				System.out.println("start check exam: " + ed.getExamID() + " executionID=" + ed.getExecutionID());
				try {
					Statement stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					ResultSet rs2 = stmt2.executeQuery("SELECT s.* FROM studentresultinexam AS s WHERE s.examID="
							+ ed.getExamID() + " AND s.executionID=" + ed.getExecutionID() + " AND s.computerized=1 ");
					ArrayList<StudentInExam> arr = new ArrayList<StudentInExam>();
					while (rs2.next()) {
						StudentInExam student = new StudentInExam();
						student.setStudentID(rs2.getString(3));
						student.setExamID(ed.getExamID());
						student.setExecutionID(ed.getExecutionID());
						student.setIsComp(rs2.getBoolean(4));
						student.setGrade(rs2.getInt(5));
						Statement stmt3 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						ResultSet rs3 = stmt3
								.executeQuery("SELECT * FROM studentanswersinexam AS sa,Question as q WHERE examID="
										+ ed.getExamID() + " AND executionID=" + ed.getExecutionID() + " AND studentID="
										+ student.getStudentID() + " AND q.questionID=sa.questionID");

						int j = 0;
						while (rs3.next()) {
							Question q = new Question(rs3.getString(7), rs3.getString(4), rs3.getString(8),
									rs3.getString(9), null, null, null, null, rs3.getInt(10));
							QuestionInExam qie = new QuestionInExam(rs3.getInt(5), q, j++);
							student.getCheckedAnswers().put(qie, rs3.getInt(5));
						}
						arr.add(student);
					}
					int sum;
					int histogram[] = new int[10];
					for (int k = 0; k < 10; k++)
						histogram[k] = 0;
					int numOfStudents;
					HashMap<StudentInExam, ArrayList<Integer>> result = ce.checkExams(arr);
					for (Map.Entry<StudentInExam, ArrayList<Integer>> entry : result.entrySet()) {
						Statement stmt4 = (Statement) conn.createStatement();
						String str = "SELECT * FROM studentresultinexam AS res WHERE res.examID=" + ed.getExamID()
								+ " AND res.executionID=" + ed.getExecutionID() + " AND studentID="
								+ entry.getKey().getStudentID();
						ResultSet rs4 = stmt4.executeQuery(str);
						rs4.next();
						rs4.updateInt(5, entry.getValue().get(0));
						rs4.updateInt(7, entry.getValue().get(1));
						rs4.updateInt(8, entry.getValue().get(2));
						rs4.updateRow();
						System.out.println(
								"finished checking exam: " + ed.getExamID() + " executionID=" + ed.getExecutionID());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		checkExamThread.start();

	}

	private void checkIfAllStudentFinished(ExecutionDetails ed, Connection conn) throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM examInExecution Where examID=" + ed.getExamID()
				+ " AND executionID=" + ed.getExecutionID());
		rs.next();
		boolean isGroup = rs.getBoolean(11);
		if (isGroup) {
			rs = stmt.executeQuery("SELECT * FROM examnieegroup WHERE examID=" + ed.getExamID() + " AND executionID="
					+ ed.getExecutionID() + " AND (studentStatus=\"notStarted\" OR studentStatus=\"started\")");
			if (!rs.next()) {
				Message msg = new Message();
				lockExam(msg, null, conn);
			}
		} else {
			rs = stmt.executeQuery("SELECT * FROM aes.studentincourse AS s WHERE s.subjectID="
					+ ed.getExamID().substring(0, 1) + " AND s.courseID=" + ed.getExamID().substring(2, 3)
					+ " AND not exists(SELECT * FROM studentresultinexam as sr where sr.studentID=s.studentID AND sr.examID="
					+ ed.getExamID() + " AND sr.executionID=" + ed.getExecutionID() + ")");
			if (!rs.next()) {
				Message msg = new Message();
				lockExam(msg, null, conn);
			}
		}
	}

	private void changeStudentInExamStatus(Message msg, ConnectionToClient client, Connection conn2)
			throws SQLException, IOException {
		StudentInExam student = (StudentInExam) msg.getSentObj();
		Boolean isGroup;
		ExecutionDetails ed = new ExecutionDetails(student.getExamID(), student.getExecutionID());
		if (student.getStudentStatus().equalsIgnoreCase("started")) {
			if (exemanieeList.containsKey(ed))
				exemanieeList.get(ed).add(student);
			else {
				ArrayList<StudentInExam> arr = new ArrayList<StudentInExam>();
				arr.add(student);
				exemanieeList.put(ed, arr);
			}
			sendToAllClients(exemanieeList);
		} else if (student.getStudentStatus().equalsIgnoreCase("Finished")) {
			System.out.println("tring to submit");
			ArrayList<StudentInExam> arr = new ArrayList<StudentInExam>();
			arr.add(student);
			StudentInExam temp = student;
			temp.setStudentStatus("started");
			Iterator it = exemanieeList.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (ed.equals(pair.getKey())) {
					((ArrayList) pair.getValue()).remove(temp);

				}
			}
			Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM studentresultinexam");
			rs.moveToInsertRow();
			rs.updateString(1, student.getExamID());
			rs.updateInt(2, student.getExecutionID());
			rs.updateString(3, student.getStudentID());
			rs.updateInt(4, student.getIsComp() ? 1 : 0);
			rs.updateInt(5, -1);
			rs.updateInt(6, student.getActualDuration());
			rs.insertRow();
			if (student.getIsComp()) {
				Statement stmt2 = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM studentanswersinexam");
				Iterator it2 = student.getCheckedAnswers().entrySet().iterator();
				while (it2.hasNext()) {
					Map.Entry pair = (Map.Entry) it2.next();
					rs2.moveToInsertRow();
					rs2.updateString(1, student.getExamID());
					rs2.updateInt(2, student.getExecutionID());
					rs2.updateString(3, student.getStudentID());
					rs2.updateString(4, ((Question) pair.getKey()).getQuestionID());
					rs2.updateInt(5, (int) pair.getValue());
				}
			}
			checkIfAllStudentFinished(ed, conn2);
		} else if (student.getStudentStatus().equalsIgnoreCase("notFinished")) {
			ArrayList<StudentInExam> arr = new ArrayList<StudentInExam>();
			arr.add(student);
			StudentInExam temp = student;
			temp.setStudentStatus("started");
			Iterator it = exemanieeList.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (ed.equals(pair.getKey())) {
					((ArrayList) pair.getValue()).remove(temp);
				}
			}
			Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM studentresultinexam");
			rs.moveToInsertRow();
			rs.updateString(1, student.getExamID());
			rs.updateInt(2, student.getExecutionID());
			rs.updateString(3, student.getStudentID());
			rs.updateInt(4, student.getIsComp() ? 1 : 0);
			rs.updateInt(5, 0);
			rs.updateInt(6, student.getActualDuration());
			rs.insertRow();
			checkIfAllStudentFinished(ed, conn2);
		}
	}

	private void sendOverTimeRequest(Message msg, ConnectionToClient client, Connection conn2)
			throws IOException, SQLException {
		OvertimeDetails overtime = (OvertimeDetails) msg.getSentObj();
		Iterator it = connectedClients.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (((User) pair.getKey()).getTitle().equalsIgnoreCase("Principle")) {
				System.out.println(((User) pair.getKey()));
				Message m = new Message();
				m.setReturnObj("newOverTimeRequest");
				((ConnectionToClient) pair.getValue()).sendToClient(m);
			}
		}
		System.out.println("trtretetret");
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM overtimerequests");
		rs.moveToInsertRow();
		rs.updateString(1, overtime.getExamID());
		rs.updateInt(2, overtime.getExecutionID());
		rs.updateString(3, overtime.getReason());
		rs.updateInt(4, overtime.getRequestedTime());
		rs.insertRow();
		stmt.close();
		rs.close();

	}

	private void getAllOverTimeRequests(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM overtimerequests");
		rs.moveToInsertRow();
		ArrayList<OvertimeDetails> arrOfovertime = new ArrayList<OvertimeDetails>();
		while (rs.next()) {
			OvertimeDetails otd = new OvertimeDetails(rs.getString(1), rs.getInt(2), rs.getInt(4), rs.getString(3),
					false);
			arrOfovertime.add(otd);
		}
		msg.setReturnObj(arrOfovertime);
		client.sendToClient(msg);
		stmt.close();
		rs.close();

	}

	private void denyOvertime(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		OvertimeDetails overtime = (OvertimeDetails) msg.getSentObj();
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM overtimerequests WHERE examID=" + overtime.getExamID()
				+ " AND executionID=" + overtime.getExecutionID());
		rs.last();
		rs.deleteRow();
		rs = stmt.executeQuery("SELECT * FROM examInExecution WHERE examID=" + overtime.getExamID()
				+ " AND executionID=" + overtime.getExecutionID());
		rs.last();
		User u = new User();
		u.setuID(rs.getString(4));
		Iterator it2 = this.connectedClients.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry pair = (Map.Entry) it2.next();
			if (u.equals(pair.getKey())) {
				Message m = new Message();
				m.setReturnObj(overtime);
				m.setqueryToDo("overtimeApproved");
				((ConnectionToClient) pair.getValue()).sendToClient(m);
			}
		}
	}

	private void approveOvertime(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		OvertimeDetails overtime = (OvertimeDetails) msg.getSentObj();
		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("SELECT * FROM overtimerequests WHERE examID=" + overtime.getExamID()
				+ " AND executionID=" + overtime.getExecutionID());
		rs.last();
		rs.deleteRow();
		Iterator it = this.exemanieeList.entrySet().iterator();
		ArrayList<StudentInExam> students = new ArrayList<StudentInExam>();
		ExecutionDetails ed = new ExecutionDetails(overtime.getExamID(), overtime.getExecutionID());
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (ed.equals(pair.getKey())) {
				students = ((ArrayList) pair.getValue());
				break;
			}
		}
		for (StudentInExam s : students) {
			Iterator it2 = this.connectedClients.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry pair = (Map.Entry) it2.next();
				User u = new User();
				u.setuID(s.getStudentID());
				u.setuName(s.getStudentName());
				if (u.equals(pair.getKey())) {
					Message m = new Message();
					m.setReturnObj(overtime);
					m.setqueryToDo("overtimeApproved");
					((ConnectionToClient) pair.getValue()).sendToClient(m);
					break;
				}
			}
		}
		rs = stmt.executeQuery("SELECT * FROM examInExecution WHERE examID=" + overtime.getExamID()
				+ " AND executionID=" + overtime.getExecutionID());
		rs.last();
		User u = new User();
		u.setuID(rs.getString(4));
		Iterator it2 = this.connectedClients.entrySet().iterator();
		while (it2.hasNext()) {
			Map.Entry pair = (Map.Entry) it2.next();
			if (u.equals(pair.getKey())) {
				Message m = new Message();
				m.setReturnObj(overtime);
				m.setqueryToDo("overtimeApproved");
				((ConnectionToClient) pair.getValue()).sendToClient(m);
			}
		}
	}

	private void statisticsforCourse(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Course c = (Course) msg.getSentObj();
		ArrayList<ExamReport> reports = new ArrayList<ExamReport>();
		Statement stmt = (Statement) conn.createStatement();
		String s = "select * from examInExecution as ee, reportforexam as r,"
				+ "(select e.examID from exam as e where e.subjectID=" + c.getSubject().getSubjectID()
				+ " and e.courseID=" + c.getcID() + ") as where ee.examID=t.examID AND "
				+ "ee.examId=r.examID and ee.executionID=r.executionID";
		ResultSet rs = stmt.executeQuery(s);
		while (rs.next()) {
			ExamReport er = new ExamReport();
			er.setExamID(rs.getString(1));
			er.setExecutionID(rs.getInt(2));
			er.setAvg(rs.getFloat(14));
			er.setMidean(rs.getInt(15));
			int[] per = new int[10];
			for (int i = 0; i < 10; i++) {
				per[i] = rs.getInt(i + 16);
			}
			er.setPercentages(per);
			reports.add(er);
		}
		stmt.close();
		rs.close();
		msg.setReturnObj(reports);
		client.sendToClient(msg);
	}

	private void getAllExamReportsTeacherWrote(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		User u = (User) msg.getSentObj();
		String s = "select r.* from examInExecution as ee, reportforexam as r,"
				+ "(SELECT e.examID FROM  exam as e where e.teacherID=" + u.getuID() + ")"
				+ " as t where ee.examID=t.examID AND ee.examId=r.examID and ee.executionID=r.executionID";

		ArrayList<ExamReport> reports = new ArrayList<ExamReport>();
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(s);
		while (rs.next()) {
			ExamReport er = new ExamReport();
			er.setExamID(rs.getString(1));
			er.setExecutionID(rs.getInt(2));
			er.setAvg(rs.getFloat(3));
			er.setMidean(rs.getInt(4));
			int[] per = new int[10];
			for (int i = 0; i < 10; i++) {
				per[i] = rs.getInt(i + 5);
			}
			er.setPercentages(per);
			reports.add(er);
		}
		stmt.close();
		rs.close();
		msg.setReturnObj(reports);
		client.sendToClient(msg);

	}

	private void getAllExamsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT * FROM exam AS E, courseInSubject AS C, subject AS S, user as u\r\n"
				+ "WHERE E.subjectID=C.subjectID AND E.courseID=C.courseID AND E.subjectID=S.subjectID and u.userID=E.teacherID";
		ResultSet rs = stmt.executeQuery(s);
		ArrayList<Exam> tempArr = new ArrayList<Exam>();
		while (rs.next()) {
			Exam e = new Exam();
			e.setExamID(rs.getString(1));
			e.setTeacherID(rs.getString(2));
			Subject sub = new Subject(rs.getString(7), rs.getString(14));
			e.setCourse(new Course(rs.getString(8), rs.getString(11), null, sub));
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setWasUsed(rs.getBoolean(3));
			e.setTeacherName(rs.getString(17));
			tempArr.add(e);
		}
		for (int i = 0; i < tempArr.size(); i++) {
			rs = stmt.executeQuery("SELECT * FROM questionInExam AS qe, question AS q where qe.questionID=q.questionID"
					+ " and qe.examID=" + tempArr.get(i).getExamID());
			HashMap<Question, Integer> map = new HashMap<Question, Integer>();
			while (rs.next()) {
				Question q = new Question();
				q.setQuestionID(rs.getString(2));
				q.setQuestionTxt(rs.getString(5));
				String[] ans = new String[4];
				Statement stmt2 = (Statement) conn.createStatement();
				ResultSet rs2 = stmt2
						.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
				for (int j = 0; rs2.next(); j++) {
					ans[j] = rs2.getString(3);
				}
				rs2.close();
				stmt2.close();
				q.setAnswers(ans);
				q.setTeacherID(rs.getString(6));
				// q.setTeacherName(rs.getString(11));
				q.setCorrectAnswer(rs.getInt(8));
				q.setInstruction(rs.getString(7));
				map.put(q, rs.getInt(3));
			}
			tempArr.get(i).setQuestions(map);
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void getAllQuestionsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT * FROM question AS Q, user AS U WHERE U.userID=Q.teacherID ";
		// This SHEILTA returns all the exams with their subject name and course name.
		ArrayList<Question> tempArr = new ArrayList<Question>();
		ResultSet rs = stmt.executeQuery(s);
		// rs is an array with all of the SHEILTA results details.
		while (rs.next()) {
			Question q = new Question();
			q.setQuestionID(rs.getString(1));
			q.setQuestionTxt(rs.getString(2));
			q.setTeacherName(rs.getString(8));
			q.setCorrectAnswer(rs.getInt(5));
			q.setTeacherID(rs.getString(7));
			q.setInstruction(rs.getString(4));
			// from here we match each question with its answers.
			String[] ans = new String[4];
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("SELECT * FROM answersInQuestion AQ WHERE AQ.questionID=" + q.getQuestionID());
			int j = 0;
			while (rs2.next()) {
				ans[j++] = rs2.getString(3);
			}
			rs2.close();
			stmt2.close();
			q.setAnswers(ans);
			tempArr.add(q);
		}
		// From here we returns the details:
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	// This method return all the courses in the data base.
	private void getAllCoursesInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT s.subjectID, s.subjectName,c.courseID,c.courseName FROM subject as s, courseinsubject as c"
				+ " where s.subjectID=c.subjectID";
		ArrayList<Course> courseList = new ArrayList<Course>();
		ResultSet rs = stmt.executeQuery(s);

		while (rs.next()) {
			Subject sub = new Subject(rs.getString(1), rs.getString(2));
			courseList.add(new Course(rs.getString(3), rs.getString(4), null, sub));
		}
		stmt.close();
		rs.close();

		msg.setReturnObj(courseList);
		client.sendToClient(msg);
	}

	// This method return all the subjects in the data base.
	private void getAllQuestionInExam(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"SELECT * FROM questioninexam as qie, question as q" + " where qie.questionID=q.questionID");
		ArrayList<QuestionInExam> qieArr = new ArrayList<QuestionInExam>();
		int j = 1;
		while (rs.next()) {

			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("SELECT * FROM answersinquestion as a" + " where a.questionID=" + rs.getString(2));
			int i = 0;
			String[] arr = new String[4];
			while (rs2.next()) {
				arr[i++] = rs2.getString(1);
			}
			Question q = new Question(rs.getString(5), rs.getString(4), rs.getString(6), rs.getString(7), arr[0],
					arr[1], arr[2], arr[3], rs.getInt(8));
			qieArr.add(new QuestionInExam(rs.getInt(3), q, j++));
		}
		stmt.close();
		rs.close();
		msg.setReturnObj(qieArr);
		client.sendToClient(msg);
	}

	// This method return all the subjects in the data base.
	private void getAllStudentInExam(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM studentresultinexam AS sr , exam AS e , courseinsubject AS cs "
				+ "WHERE sr.approved=1 AND sr.examID=e.examID AND e.subjectID=cs.subjectID AND e.courseID=cs.courseID");
		ArrayList<StudentInExam> tempArr = new ArrayList<StudentInExam>();// to save all the relevant exams grade of rhe

		while (rs.next()) {
			StudentInExam sGrade = new StudentInExam(rs.getString(1), rs.getInt(5), rs.getTimestamp(11),
					rs.getString(22), rs.getString(3));
			sGrade.setExecutionID(rs.getInt(2));
			sGrade.setNumberOfCorrectAnswer(rs.getInt(7));
			sGrade.setNumberOfWrongAnswer(rs.getInt(8));
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2
					.executeQuery("SELECT * FROM questioninexam as qie, question as q, studentanswersinexam as s "
							+ "where qie.questionID=q.questionID and s.questionID=q.questionID and s.examId="
							+ rs.getString(1));
			HashMap<QuestionInExam, Integer> que = new HashMap<QuestionInExam, Integer>();
			int j = 1;
			while (rs2.next()) {

				Statement stmt3 = (Statement) conn.createStatement();
				ResultSet rs3 = stmt3.executeQuery(
						"SELECT * FROM answersinquestion as a" + " where a.questionID=" + rs2.getString(2));
				int i = 0;
				String[] arr = new String[4];
				while (rs3.next()) {
					arr[i++] = rs3.getString(3);
				}
				rs3.close();
				stmt3.close();
				Question q = new Question(rs2.getString(5), rs2.getString(4), rs2.getString(6), rs2.getString(7),
						arr[0], arr[1], arr[2], arr[3], rs2.getInt(8));
				QuestionInExam qieArr = new QuestionInExam(rs2.getInt(3), q, j++);
				que.put(qieArr, rs2.getInt(14));
			}
			sGrade.setCheckedAnswers(que);
			stmt2.close();
			rs2.close();
			tempArr.add(sGrade);
		}
		rs.close();
		stmt.close();
		System.out.println("hjwhdwehfewhfoewfewjfoehfroo");
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	// This method return all the subjects in the data base.
	private void getAllExamInExecution(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = " SELECT  E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName  , EE.executingTeacherID , EE.executionID , EE.locked , EE.examcode ,EE.isGroup"
				+ " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U"
				+ " WHERE EE.examID=E.examID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID"
				+ " AND EE.isGroup=0 AND EE.executingTeacherID=U.userID AND E.courseID=C.courseID AND E.subjectID=C.subjectID AND E.subjectID=S.subjectID AND EE.locked=0"
				+ " union "
				+ " SELECT E.examID , E.teacherID , E.USED , E.teacherInstruction , E.studentInstruction , E.duration , E.subjectID , E.courseID , C.courseName , S.subjectName , U.userName , EE.executingTeacherID , EE.executionID , EE.locked , EE.examcode ,EE.isGroup "
				+ " FROM examinexecution as EE, examnieegroup AS EG, exam AS E, studentincourse SC , courseInSubject AS C , subject AS S , user AS U "
				+ " WHERE EE.examID=E.examID AND EE.locked=0 AND EE.executingTeacherID=U.userID AND E.subjectID=SC.subjectID AND E.courseID=SC.courseID AND E.subjectID=C.subjectID AND E.courseID=C.courseID AND E.subjectID=S.subjectID"
				+ " AND EE.isGroup=1 AND EG.examID=EE.examID AND EG.executionId=EE.executionID";

		ResultSet rs = stmt.executeQuery(s); // sent the sql as stinrg
		ArrayList<ExamInExecution> tempArr = new ArrayList<ExamInExecution>();

		while (rs.next()) {
			ExamInExecution ee = new ExamInExecution(); // create the new ExamInExecution
			Exam e = new Exam(); // create the new exam that execute

			e.setExamID(rs.getString(1));
			e.setTeacherID(rs.getString(2)); // SAVE THE ID OF THE TEACHER THAT WROTE THE EXAM
			e.setWasUsed(rs.getInt(3) == 1); // CHANGE THE STATUS OF THE EXAM
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setCourse(new Course(rs.getString(8), rs.getString(9), rs.getString(2),
					(new Subject(rs.getString(7), rs.getString(10)))));

			String tempID = new String(rs.getString(2)); //
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(" SELECT U.userName FROM user AS U WHERE U.userID =" + tempID);
			rs2.next();
			e.setTeacherName(rs2.getString(1));

			// ee.setExamDet(e);
			ee.setExecutionID(rs.getInt(13));
			ee.setLocked(rs.getBoolean(14));
			ee.setExamCode(rs.getString(15));
			ee.setIsGroup(rs.getBoolean(16));
			ee.setSubjectID(rs.getString(7));
			ee.setCourseID(rs.getString(8));
			ee.setCourseName(rs.getString(9));
			// finish to create the exam
			User u = new User();
			u.setuID(rs.getString(12));
			u.setuName(rs.getString(11));
			ee.setExecTeacher(u);

			// finish to create the exam to execute

			HashMap<Question, Integer> questionsInExam = new HashMap<Question, Integer>();
			rs2 = stmt2.executeQuery("SELECT * FROM question as q, questioninexam as qic"
					+ " WHERE qic.questionID=q.questionID AND qic.examID=" + e.getExamID());

			while (rs2.next()) {
				String temp = rs2.getString(1);
				Statement stmt3 = (Statement) conn.createStatement();
				String str = "SELECT * FROM answersinquestion AS ans WHERE ans.questionID=" + temp;
				ResultSet rs3 = stmt3.executeQuery(str);
				String[] answers = new String[4];
				int i = 0;
				while (rs3.next()) {
					answers[i++] = rs3.getString(3);
				}
				rs3.close();
				Question q = new Question(rs2.getString(2), rs2.getString(1), rs2.getString(3), rs.getString(4),
						answers[0], answers[1], answers[2], answers[3], rs2.getInt(5));
				questionsInExam.put(q, rs2.getInt(9));
			}
			e.setQuestions(questionsInExam);

			ee.setExamDet(e);
			tempArr.add(ee); // add all the exam in exacution to the arr
			rs2.close();
			stmt2.close();
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	// This method return all the subjects in the data base.
	private void getAllSubjectsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM subject");
		ArrayList<Subject> sub = new ArrayList<Subject>();
		while (rs.next()) {
			sub.add(new Subject(rs.getString(1), rs.getString(2)));
		}
		stmt.close();
		rs.close();
		msg.setReturnObj(sub);
		client.sendToClient(msg);
	}

	private void GetGradeByStudentDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		User StudentToSearch = (User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery("SELECT sr.examID ,sr.executionID, sr.grade, sr.examDate , cs.courseName "
				+ "FROM studentresultinexam AS sr , exam AS e , courseinsubject AS cs "
				+ "WHERE sr.approved=1 AND sr.studentID=" + StudentToSearch.getuID()
				+ " AND sr.examID=e.examID AND e.subjectID=cs.subjectID AND e.courseID=cs.courseID");
		ArrayList<StudentInExam> tempArr = new ArrayList<StudentInExam>();// to save all the relevant exams grade of rhe

		while (rs.next()) {
			StudentInExam sGrade = new StudentInExam(rs.getString("examID"), rs.getInt("grade"),
					rs.getTimestamp("examDate"), rs.getString("courseName"), StudentToSearch.getuID());
			sGrade.setExecutionID(rs.getInt("executionID"));
			tempArr.add(sGrade);

		}
		rs.close();
		stmt.close();
		System.out.println("hjwhdwehfewhfoewfewjfoehfroo");
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void getStudentResultInExam(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

		StudentInExam sie = (StudentInExam) msg.getSentObj();
		Statement stmt = (Statement) conn.createStatement();
		int cnt = 0;
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM studentanswersinexam AS SAE WHERE SAE.studentID=" + sie.getStudentID()
						+ " AND SAE.examId=" + sie.getExamID() + " AND SAE.executionID=" + sie.getExecutionID());
		HashMap<QuestionInExam, Integer> map = new HashMap<QuestionInExam, Integer>();
		while (rs.next()) {
			int ans = rs.getInt(5);
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(" SELECT * FROM Question AS Q, questioninexam as qe WHERE "
					+ "Q.questionID=qe.questionID and  qe.examID=" + rs.getString(1));
			while (rs2.next()) {
				String temp = rs2.getString(1);
				Statement stmt3 = (Statement) conn.createStatement();
				String str = "SELECT * FROM answersinquestion AS ans WHERE ans.questionID=" + temp;
				ResultSet rs3 = stmt3.executeQuery(str);
				String[] answers = new String[4];
				int i = 0;
				while (rs3.next()) {
					answers[i++] = rs3.getString(3);
				}

				Question q = new Question(rs2.getString(2), rs2.getString(1), rs2.getString(3), rs2.getString(4),
						answers[0], answers[1], answers[2], answers[3], rs2.getInt(5));
				QuestionInExam qie = new QuestionInExam(rs.getInt(9), q, cnt++);
				map.put(qie, ans);

				rs3.close();
				stmt3.close();
			}
			rs2.close();
			stmt2.close();
		}
		sie.setCheckedAnswers(map);

		msg.setReturnObj(sie);
		rs.close();
		stmt.close();
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

}
// End of EchoServer class
