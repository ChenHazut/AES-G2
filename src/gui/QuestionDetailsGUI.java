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

public class QuestionDetailsGUI extends QuestionForm
{
	
	Question q;
	
	public QuestionDetailsGUI() 
	{
		super();
		q=m.getSelectedQuestion();
		
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		System.out.println("bla bla bla bla");
		Parent root = FXMLLoader.load(getClass().getResource("QuestionDetails.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}

	
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
		subjectCombo.setVisible(false);
		courseCombo.setVisible(false);
	}



	
}
