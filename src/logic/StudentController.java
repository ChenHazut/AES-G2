package logic;

import java.io.Serializable;
import java.util.ArrayList;

import common.Message;

public class StudentController {

	User student; //the user is student type
	LoginController lc; //save all the login parameters
	ClientConsole client;
	
	public StudentController() //constructor
	{
		lc=new LoginController();  
		student=lc.getUser();
		client=new ClientConsole();
		//save all the student info
	}
	
	public ArrayList<StudentInExam> getAllgrades()//send request to db to get all grades of the student  
	{
		Message msg=new Message();
		msg.setSentObj(student); //save the object of client
		msg.setqueryToDo("getAllGradesRelevantToStusent");
		msg.setClassType("Student");
		client.accept(msg);//send the message to the client
		
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		msg=client.getMessage();
		ArrayList<StudentInExam> arrOfGrades;
		arrOfGrades=(ArrayList<StudentInExam>)msg.getReturnObj();
		System.out.println("***************");
		return arrOfGrades;
		
	}
	
	
	public ArrayList<Exam> getAllPerExams() {
		Message msg=new Message();
		msg.setSentObj(student);
		msg.setqueryToDo("getAllPerformExamsRelevantToStudent");
		msg.setClassType("Student");
		
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg=client.getMessage();
		ArrayList<Exam> arrOfExams=new ArrayList<Exam>();
		arrOfExams=(ArrayList<Exam>)msg.getReturnObj();
		return arrOfExams;
	}
}
