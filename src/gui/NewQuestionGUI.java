package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import common.Message;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Course;
import logic.Question;
import logic.Subject;
import logic.TeacherController;
import logic.User;

public class NewQuestionGUI implements Initializable
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
	Label combosErr;
	@FXML
	ComboBox<String> subjectCombo;
	@FXML
	ComboBox<String> courseCombo;
	
	Question q;
	ClientConsole client;
	GUImanager m;
	User teacher;
	TeacherController tc;
	
	public NewQuestionGUI() 
	{
		client=new ClientConsole();
		m=new GUImanager();
		tc= new TeacherController();
		teacher=tc.getTeacher();
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("NewQuestion.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		teacherNameLabel.setText(teacher.getuName());
		teacherNameLabel.setDisable(true);
		tc.getTeacherCourse();
		for(int i=0;i<tc.getSubjects().size();i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		
	}

	protected Question getFilledDetails()
	{
		int flag=0;
		int flagAns=0;
		Question updatedQuestion=new Question();
		
		updatedQuestion.setTeacherName(tc.getTeacher().getuName());
		updatedQuestion.setTeacherID(tc.getTeacher().getuID());
		if(QuestionLabel.getText().equals(""))
		{
			qtxt.setText("*");
			flag=1;
		}
		else updatedQuestion.setQuestionTxt(QuestionLabel.getText());
		updatedQuestion.setInstruction(instructionLabel.getText());
		if(answer1Label.getText().equals(""))
		{
			qans1.setText("*");
			flag=1;
		}
		if(answer2Label.getText().equals(""))
		{
			qans2.setText("*");
			flag=1;
		}
		if(answer3Label.getText().equals(""))
		{
			qans3.setText("*");
			flag=1;
		}
		if(answer4Label.getText().equals(""))
		{
			qans4.setText("*");
			flag=1;
		}
		if(flag==0)
			updatedQuestion.setAnswers(answer1Label.getText(), answer2Label.getText(), answer3Label.getText(), answer4Label.getText());
		if(correctAnswerLabel.getText().equals(""))
		{
			corAns.setText("*");
			flag=1;
		}
		else updatedQuestion.setCorrectAnswer(Integer.parseInt(correctAnswerLabel.getText()));
		if(flag==0)
			return updatedQuestion;	
		return null;
	}
	
	public void saveButtonAction(ActionEvent ae) throws Exception
	{ 
		System.out.println("save has been pressed");
		Question updatedQuestion= getFilledDetails();
		if(updatedQuestion==null)
			return;
		
		if (subjectCombo.getValue()==null||courseCombo.getValue()==null)
		{
			System.out.println("no subject selected");
			combosErr.setText("*");
			return;
		}
		Subject s = null;
		for(int i=0;i<tc.getSubjects().size();i++)
		{
			if(tc.getSubjects().get(i).getsName().equals(subjectCombo.getValue()))
				s=tc.getSubjects().get(i);
		}
		
		updatedQuestion.setQuestionID(s.getSubjectID()+s.getNextQID());
		q=tc.createNewQuestion(updatedQuestion);
		
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
	
	public void subjectComboBoxAction(ActionEvent ae)
	{
		int i;
		for(i=0;i<tc.getCourses().size();i++)
			if(tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				courseCombo.getItems().add(tc.getCourses().get(i).getcName());
	}
	
	public void courseComboBoxAction(ActionEvent ae)
	{
		
	}
	
}
