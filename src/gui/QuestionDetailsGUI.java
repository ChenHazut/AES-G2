package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;

public class QuestionDetailsGUI extends QuestionForm implements Initializable 
{
	
	Question q;
	GUImanager m;
	

	ClientConsole client;
	
	public QuestionDetailsGUI() 
	{
		client=new ClientConsole();
		m=new GUImanager();
		q=m.getSelectedQuestion();
	}
	
	@Override
	public void saveButtonAction(ActionEvent ae) throws Exception
	{ 
		
		System.out.println("save has been pressed");
		Message questionToSend=new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("updadeQuestion");
		Question updatedQuestion= getFilledDetails();
		questionToSend.setSentObj(updatedQuestion);
		client.accept(questionToSend);
		try 
		{
			Thread.sleep(1500L);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		q=updatedQuestion;
		m.setUpdatedQuestion(q);
		Stage stage = (Stage) saveButton.getScene().getWindow();
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		qrg.start(stage);
	}
	
	@Override
	public void cancleButtonAction(ActionEvent ae) throws Exception
	{
		System.out.println("cancle has been pressed");
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		qrg.start(stage);
	}
	@Override
	public void correctAnswerTextField(ActionEvent ae)
	{
	
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		QuestionIDTF.setText(q.getQuestionID());
		QuestionIDTF.setDisable(true);
		QuestionLabel.setText(q.getQuestionTxt());
		answer1Label.setText(q.getAnswers()[0]);
		answer2Label.setText(q.getAnswers()[1]);
		answer3Label.setText(q.getAnswers()[2]);
		answer4Label.setText(q.getAnswers()[3]);
		teacherNameLabel.setText(q.getTeacherName());
		teacherNameLabel.setDisable(true);
		instructionLabel.setText(q.getInstruction());
		correctAnswerLabel.setText(Integer.toString(q.getCorrectAnswer()));
		
	}



	
}