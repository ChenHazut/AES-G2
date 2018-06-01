package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	
	Question q;
	
	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("QuestionDetails.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	protected Question getFilledDetails()
	{
		Question updatedQuestion=new Question();
		updatedQuestion.setQuestionID(QuestionIDTF.getText());
		updatedQuestion.setTeacherName(teacherNameLabel.getText());
		updatedQuestion.setQuestionTxt(QuestionLabel.getText());
		updatedQuestion.setInstruction(instructionLabel.getText());
		updatedQuestion.setCorrectAnswer(Integer.parseInt(correctAnswerLabel.getText()));
		updatedQuestion.setAnswers(answer1Label.getText(), answer2Label.getText(), answer3Label.getText(), answer4Label.getText());
		return updatedQuestion;
	}
	
	public abstract void saveButtonAction(ActionEvent ae) throws Exception;
	
	public abstract void cancleButtonAction(ActionEvent ae) throws Exception;
	
	public abstract void correctAnswerTextField(ActionEvent ae);
}
