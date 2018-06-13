package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import client.ChatClient;
import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;

public class QuestionDetailsGUI implements Initializable
{
	@FXML
	Label questionIDLabel;
	@FXML
	ComboBox<String> correctAnswerCombo;
	@FXML
	Button cancleButton;
	@FXML
	Button saveButton;
	@FXML
	TextArea QuestionLabel;
	@FXML
	TextArea answer1Label;
	@FXML
	TextArea answer2Label;
	@FXML
	TextArea answer3Label;
	@FXML
	TextArea answer4Label;
	@FXML
	Label teacherNameLabel;
	@FXML
	TextArea instructionLabel;

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
	Label subjectcourseL;
	@FXML
	ComboBox<String> subjectCombo;
	@FXML
	ListView coursesList;

	ObservableList<String> cList;
	Question q;
	ClientConsole client;
	GUImanager m;
	
	
	
	public QuestionDetailsGUI() 
	{
		client=new ClientConsole();
		m=new GUImanager();
		q=m.getSelectedQuestion();
		
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		System.out.println("bla bla bla bla");
		Parent root = FXMLLoader.load(getClass().getResource("editQuestion.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		questionIDLabel.setText(q.getQuestionID());
		questionIDLabel.setDisable(true);
		QuestionLabel.setText(q.getQuestionTxt());
		answer1Label.setText(q.getAnswers()[0]);
		answer2Label.setText(q.getAnswers()[1]);
		answer3Label.setText(q.getAnswers()[2]);
		answer4Label.setText(q.getAnswers()[3]);
		teacherNameLabel.setText(q.getTeacherName());
		teacherNameLabel.setDisable(true);
		instructionLabel.setText(q.getInstruction());
		correctAnswerCombo.getItems().addAll("1","2","3","4");
		correctAnswerCombo.setPromptText(Integer.toString(q.getCorrectAnswer()));
		subjectCombo.getSelectionModel().select(q.getCourseList().get(0).getSubject().getsName());
		subjectCombo.setDisable(true);
		cList=FXCollections.observableArrayList();
		for(int i=0;i<q.getCourseList().size();i++)
			cList.add(q.getCourseList().get(i).getcName());
		coursesList.getItems().add(cList);
		coursesList.disabledProperty();

	}

	protected Question getFilledDetails()
	{
		int flag=0;
		int flagAns=0;
		Question updatedQuestion=new Question();

		updatedQuestion.setQuestionID(questionIDLabel.getText());
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
		updatedQuestion.setCorrectAnswer(Integer.parseInt(correctAnswerCombo.getValue()));
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
	
}
