package logic;

import java.util.ArrayList;

import common.Message;

public class StudentController {

	User student; //the user is student type
	LoginController lc; //save all the login parameters
	ClientConsole client;
	ArrayList<Course> courses; /////////
	ArrayList<Subject> subjects; ////////////
	
	public StudentController() //constructor
	{
		lc=new LoginController();  
		student=lc.getUser();
		client=new ClientConsole();
		//save all the student info
	}
	
	public ArrayList<StudentInExam> getAllgrades()//send request to db to get all grades of the student  
	{
		return null;
		/*Message msg=new Message();
		//msg.setSentObj(student); //save the object of client
		msg.setqueryToDo("getAllGradesRelevantToStusent");
		msg.setClassType("Student");
		client.accept(msg);//send the message to the client
		
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		msg=client.getMessage();
		ArrayList<StudentInExam> arrOfQuestions=new ArrayList<StudentInExam>();
		arrOfQuestions=(ArrayList<Question>)msg.getReturnObj();
		System.out.println("***************");
		return arrOfQuestions;*/
		
	}
}
