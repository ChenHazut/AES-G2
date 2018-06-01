package gui;

import javafx.event.ActionEvent;
import logic.LoginController;
import logic.User;

public class NewQuestionGUI extends QuestionForm 
{
	LoginController lc;
	User user;
	@Override
	public void saveButtonAction(ActionEvent ae) throws Exception 
	{
		user=lc.getUser();
		 
		
	}

	@Override
	public void cancleButtonAction(ActionEvent ae) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void correctAnswerTextField(ActionEvent ae) {
		// TODO Auto-generated method stub
		
	}
	
}
