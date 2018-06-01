package logic;

import java.util.ArrayList;

import common.Message;

public class TeacherController 
{
	User teacher;
	LoginController lc;
	ClientConsole client;
	
	public TeacherController()
	{
		lc=new LoginController();
		teacher=lc.getUser();
		client=new ClientConsole();
	}
	
	public ArrayList<Question> getAllQuestions()//send request to db to get all question which belong to subject this teacher teach 
	{
		Message msg=new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllQuestionRelevantToTeacher");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg=client.getMessage();
		ArrayList<Question> arrOfQuestions=new ArrayList<Question>();
		arrOfQuestions=(ArrayList<Question>)msg.getReturnObj();
		return arrOfQuestions;
		
	}

	public void deleteQuestion(Question qToDel) 
	{
		Message msg=new Message();
		msg.setClassType("teacher");
		msg.setqueryToDo("deleteQuestion");
		msg.setSentObj(qToDel);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
