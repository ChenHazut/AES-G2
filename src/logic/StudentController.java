package logic;

import java.util.ArrayList;

import common.Message;
import gui.LoginGUI;

public class StudentController {

	User student; // the user is student type
	LoginController lc; // save all the login parameters
	ClientConsole client;
	ArrayList<Course> courses; /////////
	ArrayList<Subject> subjects; ////////////
	static StudentInExam studentResults;

	public StudentController() // constructor
	{
		lc = new LoginController();
		student = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		// save all the student info
	}

	public StudentInExam getStudentResults() {
		return studentResults;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public ArrayList<StudentInExam> getAllgrades()// send request to db to get all grades of the student
	{
		Message msg = new Message();
		msg.setSentObj(student); // save the object of client
		msg.setqueryToDo("getAllGradesRelevantToStusent");
		msg.setClassType("Student");
		client.accept(msg);// send the message to the client

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<StudentInExam> arrOfGrades;
		arrOfGrades = (ArrayList<StudentInExam>) msg.getReturnObj();
		System.out.println("***************");
		return arrOfGrades;
	}

	public ArrayList<ExamInExecution> getAllExamsInExecutin() {
		Message msg = new Message();
		msg.setSentObj(student);
		msg.setqueryToDo("getAllPerformExamsRelevantToStudent");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<ExamInExecution> arrOfExams = new ArrayList<ExamInExecution>();
		arrOfExams = (ArrayList<ExamInExecution>) msg.getReturnObj();
		return arrOfExams;
	}

	public void getStudentResultInExam(String studentID, ExamInExecution exam) {
		Message msg = new Message();

		msg.setqueryToDo("getStudentAnswersInExam");
		msg.setClassType("Student");

		StudentInExam sie = new StudentInExam();
		sie.setExamID(exam.getExamDet().getExamID());
		sie.setExecutionID(exam.getExecutionID());
		sie.setStudentID(studentID);

		msg.setSentObj(sie);
		client.accept(msg);
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();
		studentResults = (StudentInExam) msg.getReturnObj();

	}

	///// method to show the student the approved exam he choose
	public ExamInExecution getExamForStudent(StudentInExam selectedItem) {
		Message msg = new Message();
		msg.setSentObj(selectedItem);
		msg.setqueryToDo("getTheExamForStudentToShow");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution exam = new ExamInExecution();
		exam = (ExamInExecution) msg.getReturnObj();
		return exam;
	}

	public Exam getExamByExamID(ExamInExecution exam) {
		Message msg = new Message();
		msg.setSentObj(exam.getExamDet());
		msg.setqueryToDo("getExamByExamID");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		Exam e = new Exam();
		e = (Exam) msg.getReturnObj();
		return e;
	}

	public void uploadManualExam(String fileExamName) {
		Message msg = new Message();
		msg.setSentObj(fileExamName);
		msg.setqueryToDo("uploadWordFileExam");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished uploading");
	}

	///// method to show the student the approved exam he choose
	public ExamInExecution performCompExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setSentObj(exam);
		msg.setqueryToDo("getTheExamToPerformComputerized");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution examToReturn = new ExamInExecution();
		examToReturn = (ExamInExecution) msg.getReturnObj();
		return exam;
	}

	public void changeStudentInExamStatus(StudentInExam s) {
		Message msg = new Message();
		msg.setSentObj(s);
		msg.setqueryToDo("changeStudentInExamStatus");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
