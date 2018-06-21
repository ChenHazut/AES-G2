package logic;

import java.util.ArrayList;

import common.Message;
import gui.LoginGUI;

public class PrincipalController {
	User principal;
	LoginController lc;
	ClientConsole client;

	public PrincipalController() // The constructor.
	{
		lc = new LoginController();
		principal = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
	}

	/*
	 * This method calls for echo server to get all of the exams and return them the
	 * client.
	 */
	public ArrayList<Exam> getAllExamsInData() {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllExmasInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<Exam> arrOfExams = new ArrayList<Exam>();
		arrOfExams = (ArrayList<Exam>) msg.getReturnObj();
		return arrOfExams; // This is the array from the function in echo server.
	}

	/*
	 * This method calls for echo server to get all of the questions and return them
	 * the client.
	 */
	public ArrayList<Question> getAllQuestionsInData() {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllQuestionsInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<Question> arrOfQuestions = new ArrayList<Question>();
		arrOfQuestions = (ArrayList<Question>) msg.getReturnObj();
		return arrOfQuestions; // This is the array from the function in echo server.
	}

	/*
	 * This method calls for echo server to get all of the students and return them
	 * the client.
	 */
	public ArrayList<User> getAllStudentsInData() {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllStudentsInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<User> arrOfStudents = new ArrayList<User>();
		arrOfStudents = (ArrayList<User>) msg.getReturnObj();
		return arrOfStudents; // This is the array from the function in echo server.
	}

	/*
	 * This method calls for echo server to get all of the teachers and return them
	 * the client.
	 */
	public ArrayList<User> getAllTeachersInData() {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllTeachersInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<User> arrOfTeachers = new ArrayList<User>();
		arrOfTeachers = (ArrayList<User>) msg.getReturnObj();
		return arrOfTeachers; // This is the array from the function in echo server.
	}

	public ArrayList<OvertimeDetails> getAllOverTimeRequests() {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setqueryToDo("getAllOverTimeRequests");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<OvertimeDetails> arrOfovertime = new ArrayList<OvertimeDetails>();
		arrOfovertime = (ArrayList<OvertimeDetails>) msg.getReturnObj();
		return arrOfovertime; // This is the array from the function in echo server.

	}

	public void approveOvertime(OvertimeDetails overtimeDetails) {
		Message msg = new Message();
		msg.setClassType("Principal");
		msg.setqueryToDo("approveOvertime");
		msg.setSentObj(overtimeDetails);
		client.accept(msg);

	}

	public void denyOvertime(OvertimeDetails overtimeDetails) {
		// TODO Auto-generated method stub

	}
}
