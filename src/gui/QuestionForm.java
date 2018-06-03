package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;

public abstract class QuestionForm implements Initializable
{
	@FXML
	TextField correctAnswerLabel;
	@FXML
	Button cancleButton;
	@FXML
	Button saveButton;
	@FXML
	TextField QuestionLabel;
	@FXML
	TextField answer1Label;
	@FXML
	TextField answer2Label;
	@FXML
	TextField answer3Label;
	@FXML
	TextField answer4Label;
	@FXML
	TextField teacherNameLabel;
	@FXML
	TextField instructionLabel;
	@FXML
	TextField QuestionIDTF;
	@FXML
	Label qid;
	@FXML
	Label qtxt;
	@FXML
	Label qans1;
	@FXML
	Label qans2;
	@FXML
	Label qans3;
	@FXML
	Label qans4;
	@FXML
	Label corAns;
	@FXML
	ComboBox subjectCombo;
	@FXML
	ComboBox courseCombo;
	Question q;
	ClientConsole client;
	GUImanager m;
	
	
	public QuestionForm() 
	{
		client=new ClientConsole();
		m=new GUImanager();
	}
	

	protected Question getFilledDetails()
	{
		int flag=0;
		int flagAns=0;
		Question updatedQuestion=new Question();
		if(QuestionIDTF.getText().equals(""))
		{
			qid.setText("*");
			return null;
		}
		else updatedQuestion.setQuestionID(QuestionIDTF.getText());
		updatedQuestion.setTeacherName(teacherNameLabel.getText());
		if(QuestionLabel.getText().equals(""))
		{
			qtxt.setText("*");
			return null;
		}
		else updatedQuestion.setQuestionTxt(QuestionLabel.getText());
		updatedQuestion.setInstruction(instructionLabel.getText());
		if(answer1Label.getText().equals(""))
		{
			qans1.setText("*");
			return null;
		}
		if(answer2Label.getText().equals(""))
		{
			qans2.setText("*");
			return null;
		}
		if(answer3Label.getText().equals(""))
		{
			qans3.setText("*");
			return null;
		}
		if(answer4Label.getText().equals(""))
		{
			qans4.setText("*");
			return null;
		}
		updatedQuestion.setAnswers(answer1Label.getText(), answer2Label.getText(), answer3Label.getText(), answer4Label.getText());
		if(correctAnswerLabel.getText().equals(""))
		{
			corAns.setText("*");
			return null;
		}
		else updatedQuestion.setCorrectAnswer(Integer.parseInt(correctAnswerLabel.getText()));
		return updatedQuestion;	
	}
	
	
	public void saveButtonAction(ActionEvent ae) throws Exception
	{ 
		
		System.out.println("save has been pressed");
		Message questionToSend=new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("updadeQuestion");
		Question updatedQuestion= getFilledDetails();
		if(updatedQuestion==null)
			return;
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
		m.setSelectedQuestion(null);
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		qrg.start(stage);
	}

	public void cancleButtonAction(ActionEvent ae) throws Exception
	{
		System.out.println("cancle has been pressed");
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		m.setSelectedQuestion(null);
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		qrg.start(stage);
	}
	
	
	public void correctAnswerTextField(ActionEvent ae)
	{
	
	}
	
	public abstract void initialize(URL arg0, ResourceBundle arg1);
}
