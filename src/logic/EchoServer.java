package logic;

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

	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws SQLException, IOException {
		Connection conn = null;
		conn = connectToDB();
		System.out.println("SQL connection succeed");
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
	}

	private void StudentrHandler(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		System.out.println("***********student handler");
		if (msg.getqueryToDo().equals("getAllGradesRelevantToStusent")) // send to client the details // e.g to logIn
		{
		//	System.out.println("1212122121121");
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
		if (msg.getqueryToDo().equals("getTheExamToPerformComputerized")) {
			System.out.println("get the exam to do the exam computerized");
			getExamsToPerformComp(msg, client, conn);
		}
		

	}

	public void getExamsToPerformComp(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException
	{
		ExamInExecution examTemp = (ExamInExecution)msg.getSentObj();
		/// start to create the **questions from the exam
		Statement stmt4 = (Statement) conn.createStatement();
		ResultSet rs4 = stmt4.executeQuery(
				"SELECT * FROM questionInExam AS QE,question AS Q, user AS U WHERE Q.teacherID=U.userID AND QE.questionID=Q.questionID AND QE.examID="+examTemp.getExamDet().getExamID());
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
		examTemp.getExamDet().setQuestions(map);
		examTemp.getExamDet().getQuestionsArrayList();
		examTemp.getExamDet().setQuestions(null);
		rs4.close();
		stmt4.close();
		msg.setReturnObj(examTemp);
		client.sendToClient(msg);
	}

	
	// ********************************************************************************************
	// get data or change data in DB methods
	// ********************************************************************************************
	// search in db for user with same userID as sentObj in msg
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
			for (int i = 0; i < 4; i++) {
				rs2.moveToInsertRow();
				rs2.updateString(1, q.getQuestionID());
				rs2.updateString(2, Integer.toString(i));
				rs2.updateString(3, q.getAnswers()[i]);
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
		String s = "SELECT * FROM exam AS E, teacherInCourse AS TC,courseInSubject AS C,subject AS S,user AS U WHERE E.subjectID=TC.subjectID AND E.courseID=TC.courseID AND E.subjectID=S.subjectID AND C.subjectID=E.subjectID AND C.courseID=E.courseID AND U.userID=TC.teacherID AND TC.teacherID="
				+ teacherToSearch.getuID();
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
			// Statement stmt2 = (Statement) conn.createStatement();
			// ResultSet rs2 = stmt.executeQuery("SELECT * FROM exam AS E WHERE
			// examID="+ein.getExamDet().getExamID());
			// ein.getExamDet().setDuration(rs2.getInt(6));
			// ein.getExamDet().setInstructionForStudent(rs2.getString(5));
			// ein.getExamDet().setInstructionForTeacher(rs2.getString(4));
			// ein.getExamDet().setTeacherID(rs2.getString(2));
			// ein.getExamDet().getCourse().setcID(rs2.getString(8));
			// ein.getExamDet().getCourse().getSubject().setSubjectID(rs2.getString(7));
			// ein.getExamDet().setWasUsed(rs.getInt(3)==1?true:false);
			// stmt2 = (Statement) conn.createStatement();
			// rs2 = stmt.executeQuery("SELECT * FROM questioninexam AS QE WHERE
			// examID="+ein.getExamDet().getExamID());
			// while(rs2.next())
			// {
			// Question q=new Question();
			// q.setQuestionID(rs2.getString(1));
			// q.setQuestionTxt(rs2.getString(2));
			// q.setTeacherID(teacherId);
			// ein.getExamDet().getQuestions().put(arg0, arg1)
			// }
			tempArr.add(ein);
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);

	}

	private void deleteExam(Message msg, ConnectionToClient client, Connection conn) {
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Exam e = (Exam) msg.getSentObj();
			String s = "SELECT * FROM exam WHERE examIDID=" + e.getExamID();
			rs = stmt.executeQuery(s);
			rs.last();
			rs.deleteRow();
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
					+ "duration, subjectID, courseID) values (" + "\'010105\', " + "\'" + e.getTeacherID() + "\', "
					+ e.getWasUsed() + ", \'" + e.getInstructionForTeacher() + "\', \'" + e.getInstructionForStudent()
					+ "\', " + e.getDuration() + ", \'01\', \'02\')");
			// e.getCourse().getSubject()e.getCourse().getcName()

			HashMap<Question, Integer> questions = e.getQuestions();
			for (Map.Entry<Question, Integer> entry : questions.entrySet()) {
				String s = "INSERT INTO questioninexam (examID, questionID, pointsPerQuestion) values (";
				s += "\'010105\', \'" + entry.getKey().getQuestionID() + "\', " + entry.getValue() + ")";
				// s+="\'"+e.getExamID()+"\', \'"+entry.getKey().getQuestionID()+"\',
				// "+entry.getValue()+")";
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

	private void lockExam(Message msg, ConnectionToClient client, Connection conn) throws SQLException {

		Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ExamInExecution exam = (ExamInExecution) msg.getSentObj();
		System.out.println("examID= " + exam.getExamDet().getExamID());
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM examInExecution as EE WHERE EE.examID=" + exam.getExamDet().getExamID());
		rs.last();
		rs.updateInt(5, 1);
		rs.updateRow();
		rs.close();
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

	private void getAllExamsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj(); // ככל הנראה זה מיותר
		String s = "SELECT *" + " FROM exam AS E,courseInSubject AS C,user AS U " + " WHERE C.subjectID=E.subjectID "
				+ " AND C.courseID=E.courseID " + " AND E.teacherID=U.userID ";
		// This SHEILTA returns all the exams with their subject name and course name.
		ResultSet rs = stmt.executeQuery(s);
		// rs is an array with all of the SHEILTA results details.
		ArrayList<Exam> tempArr = new ArrayList<Exam>();
		// HashMap<Question,Integer> map=new HashMap<Question,Integer>();
		while (rs.next()) { // את המספרים מגלים לפי הסדר של העמודות בטבלאות לפי השאילתה. לקחת דף ולצייר את
							// זה.
			Exam e = new Exam();
			e.setExamID(rs.getString(1));
			e.setCourseName(rs.getString(11));
			e.setTeacherName(rs.getString(13));
			tempArr.add(e);
		}
		// מכאן מתחילים לסדר כל מבחן עם השאלות שלו
		// הדברים הללו נעשים בשביל שהמנהלת גם תוכל לראות את המבחנים עצמם (בלחיצה למשל)
		// ולא רק את השמות שלהם.
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
				for (int j = 0; rs2.next(); j++) // כאן אנחנו מסדרים את זה כך שלכל שאלה יש את התשובה שלה
				{
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
		// From here we returns the details:
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
		client.sendToClient(msg);
	}

	private void getAllQuestionsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		User teacherToSearch = (User) msg.getSentObj(); // ככל הנראה זה מיותר
		String s = "SELECT *" + " FROM aes.question AS Q, aes.user AS U " + " WHERE U.userID=Q.teacherID ";
		// This SHEILTA returns all the exams with their subject name and course name.
		ArrayList<Question> tempArr = new ArrayList<Question>();
		ResultSet rs = stmt.executeQuery(s);
		// rs is an array with all of the SHEILTA results details.
		while (rs.next()) {
			Question q = new Question();
			q.setQuestionID(rs.getString(1));
			q.setQuestionTxt(rs.getString(2));
			q.setTeacherName(rs.getString(8));
			// from here we match each question with its answers.
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
			tempArr.add(q);
		}
		// From here we returns the details:
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

	// This method return all the courses in the data base.
	private void getAllCoursesInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

	}

	// This method return all the subjects in the data base.
	private void getAllSubjectsInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		String s = "SELECT *" + " FROM aes.courseinsubject AS C " + " WHERE U.title = \"Teacher\" ";
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
		System.out.println("blaaaaaaaaaaaaaa");
		rs.close();
		stmt.close();
		msg.setReturnObj(tempExam);
		client.sendToClient(msg);
	}

	private void GetGradeByStudentDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {
		Statement stmt = (Statement) conn.createStatement();
		User StudentToSearch = (User) msg.getSentObj();
		ResultSet rs = stmt.executeQuery("SELECT sr.examID , sr.grade, sr.examDate , cs.courseName "
				+ "FROM studentresultinexam AS sr , exam AS e , courseinsubject AS cs "
				+ "WHERE sr.approved=1 AND sr.studentID=" + StudentToSearch.getuID()
				+ " AND sr.examID=e.examID AND e.subjectID=cs.subjectID AND e.courseID=cs.courseID");
		ArrayList<StudentInExam> tempArr = new ArrayList<StudentInExam>();// to save all the relevant exams grade of rhe
																			// student
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while (rs.next()) {
			StudentInExam sGrade = new StudentInExam(rs.getString("examID"), rs.getInt("grade"),
					rs.getTimestamp("examDate"), rs.getString("courseName"), StudentToSearch.getuID());
			tempArr.add(sGrade);

		}
		rs.close();
		stmt.close();
		System.out.println("hjwhdwehfewhfoewfewjfoehfroo");
		msg.setReturnObj(tempArr);
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
			e.setCourse(new Course(rs.getString(8), rs.getString(9), rs.getString(12),
					(new Subject(rs.getString(7), rs.getString(10)))));
			e.setWasUsed(rs.getInt(3) == 1); // CHANGE THE STATUS OF THE EXAM
			e.setInstructionForTeacher(rs.getString(4));
			e.setInstructionForStudent(rs.getString(5));
			e.setDuration(rs.getInt(6));
			e.setTeacherID(rs.getString(2)); // SAVE THE ID OF THE TEACHER THAT WROTE THE EXAM
			e.setTeacherName(rs.getString(11)); // SAVE THE TEACHER THAT WROTE THE EXAM

			ee.setExamDet(e);
			ee.setExecutionID(rs.getInt(13));
			ee.setLocked(rs.getBoolean(14));
			ee.setIsGroup(rs.getBoolean(16));
			// finish to create the exam

			ee.setCourseName(rs.getString(9));
			ee.setCourseID(rs.getString(8));
			ee.setSubjectID(rs.getString(7));
			ee.setExamCode(rs.getString(15));
			// finish to create the exam to execute

			String tempID = new String(rs.getString(12)); // CREATE STRING WITH THE ID OF THE THEACHER TO CREATE USER
			Statement stmt2 = (Statement) conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(" SELECT * FROM user AS U WHERE U.userID =" + tempID);
			rs2.next();
			User excteacher = new User(rs2.getString(1), rs2.getString(4));
			excteacher.setuName(rs2.getString(2));
			ee.setExecTeacher(excteacher);
			tempArr.add(ee); // add all the exam in exacution to the arr
			rs2.close();
			stmt2.close();
		}
		rs.close();
		stmt.close();
		msg.setReturnObj(tempArr);
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
