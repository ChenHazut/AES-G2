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

public class QuestionDetailsGUI implements Initializable
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
	
	Question q;
	GUImanager m;
	

	ClientConsole client;
	
	public QuestionDetailsGUI() 
	{
		client=new ClientConsole();
		m=new GUImanager();
		q=m.getSelectedQuestion();
	}
	
	public void saveButtonAction(ActionEvent ae)
	{ 
		
		System.out.println("save has been pressed");
		q.setCorrectAnswer(Integer.parseInt((correctAnswerLabel.getText())));
		Message questionToSend=new Message();
		questionToSend.setSentObj(q);
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("updadeQuestion");
		questionToSend.setColumnToUpdate("correctAns");
		questionToSend.setValueToUpdate(Integer.parseInt((correctAnswerLabel.getText())));
		client.accept(questionToSend);
		try 
		{
			Thread.sleep(1500L);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		Stage stage = (Stage) saveButton.getScene().getWindow();
		stage.close();
	}
	
	public void cancleButtonAction(ActionEvent ae)
	{
		System.out.println("cancle has been pressed");
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		stage.close();
	}
	
	public void correctAnswerTextField(ActionEvent ae)
	{
	
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		QuestionIDTF.setText(q.getQuestionID());
		QuestionIDTF.setDisable(true);
		QuestionLabel.setText(q.getQuestionTxt());
		QuestionLabel.setDisable(true);
		answer1Label.setText(q.getAnswers()[0]);
		answer1Label.setDisable(true);
		answer2Label.setText(q.getAnswers()[1]);
		answer2Label.setDisable(true);
		answer3Label.setText(q.getAnswers()[2]);
		answer3Label.setDisable(true);
		answer4Label.setText(q.getAnswers()[3]);
		answer4Label.setDisable(true);
		teacherNameLabel.setText(q.getTeacherName());
		teacherNameLabel.setDisable(true);
		instructionLabel.setText(q.getInstruction());
		instructionLabel.setDisable(true);
		answer1Label.setText(q.getAnswers()[0]);
		answer1Label.setDisable(true);
		correctAnswerLabel.setText(Integer.toString(q.getCorrectAnswer()));
		
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("QuestionDetails.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}

	
}
