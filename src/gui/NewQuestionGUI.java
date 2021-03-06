package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	ListView <String> courseLV;
	@FXML
	HBox comboHBOX;
	Question q;
	ClientConsole client;
	GUImanager m;
	User teacher;
	TeacherController tc;
	ObservableList<String> coursesL;
	
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
		coursesL=FXCollections.observableArrayList();
		courseLV.setItems(coursesL);
		courseLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
		
		if (subjectCombo.getValue()==null||courseLV.getSelectionModel().getSelectedItem()==null)
		{
			System.out.println("no subject or course selected");
			combosErr.setText("*");
			return;
		}
		Subject s = null;
		ArrayList<Course> selectedcourses;
		selectedcourses=new ArrayList<Course>();
		ArrayList<String> selected=new ArrayList<String>();
//		for(int i=0;i<courseLV.getSelectionModel().getSelectedItems().size();i++)
//			9selected.add(courseLV.getSelectionModel().getSelectedItems().);
		for(String c:coursesL)
		{
			selected.add(c);
		}
		int j;
		for(int i=0;i<selected.size();i++)
			for(j=0;j<tc.getCourses().size();j++)
				if(selected.get(i).equals(tc.getCourses().get(j).getcName()))
				{
					selectedcourses.add(tc.getCourses().get(j));
					break;
				}
		for(int i=0;i<tc.getSubjects().size();i++)
		{
			if(tc.getSubjects().get(i).getsName().equals(subjectCombo.getValue()))
				s=tc.getSubjects().get(i);
		}
		updatedQuestion.setCourseList(selectedcourses);
		updatedQuestion.setQuestionID(s.getSubjectID());
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
		for(i=0;i<coursesL.size();i++)
			coursesL.remove(i);
		System.out.println(tc.getCourses().size());
		for(i=0;i<tc.getCourses().size();i++)
			if(tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				coursesL.add(tc.getCourses().get(i).getcName());
			
				
	}
	
	
	
}
