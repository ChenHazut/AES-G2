package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class QuestionRepositoryGUI implements Initializable
{
	@FXML
	private TableView table;
	@FXML
	private TableColumn <QuestionGUI,String> questionID;
	@FXML
	private TableColumn <QuestionGUI,String> QuestionTxt;
	@FXML
	private TableColumn <QuestionGUI,String> teacherName;
	@FXML
	private TableColumn <QuestionGUI,ImageView> labelCol;
	@FXML
	private Button editQuestionButton;
	@FXML
	private Button insert;
	@FXML
	private ImageView image;
	private ArrayList<Question> arr;
	//private DatabaseControl dbControl;
	private QuestionDetailsGUI qdg;
	//private Main main;
	ObservableList<QuestionGUI> questionList ;
	ClientConsole client;
	GUImanager m;
	TeacherController tc;
	
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("QuestionRepository.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show(); 
	}
	
	public void insertButtonAction(ActionEvent ae) throws IOException
	{
		System.out.println("question is added");
		Stage stage = (Stage) insert.getScene().getWindow();
		NewQuestionGUI nqg=new NewQuestionGUI();
		nqg.start(stage);
	}
	
	public void deleteQuestionButtonAction(ActionEvent ae)
	{
		System.out.println("question is deleted");
		QuestionGUI q=(QuestionGUI) table.getSelectionModel().getSelectedItem();
		Question qToDel=new Question();
		qToDel.setQuestionID(q.getQuestionID());
		if(qToDel==null)
			return;
		tc.deleteQuestion(qToDel);
		for(int i=0;i<questionList.size();i++)
			if(questionList.get(i).getQuestionID().equals(qToDel.getQuestionID()))
			{
				questionList.remove(i);
				break;
			}
	}
	
	public void editQuestionButtonAction(ActionEvent ae) throws Exception
	{
		QuestionGUI q=(QuestionGUI) table.getSelectionModel().getSelectedItem();
		if(q==null)
			return;
		Stage stage = (Stage) editQuestionButton.getScene().getWindow();
		Question qToEdit=new Question();
		qToEdit.setQuestionID(q.getQuestionID());
		for(int i=0;i<arr.size();i++)
			if(arr.get(i).getQuestionID().equals(qToEdit.getQuestionID()))
			{
				qToEdit.setQuestionTxt(arr.get(i).getQuestionTxt());
				qToEdit.setTeacherID(arr.get(i).getTeacherName());
				qToEdit.setCourseList(arr.get(i).getCourseList());
				qToEdit.setAnswers(arr.get(i).getAnswers());
				qToEdit.setCorrectAnswer(arr.get(i).getCorrectAnswer());
				qToEdit.setInstruction(arr.get(i).getInstruction());
				qToEdit.setTeacherName(arr.get(i).getTeacherName());
				break;
			}
		m.selectedQuestion=qToEdit;
		qdg = new QuestionDetailsGUI();
		qdg.start(stage);
		System.out.println("question is changed");
		
		
	}
	
	public QuestionRepositoryGUI()
	{
		client= new ClientConsole();
		m=new GUImanager();
		tc=new TeacherController();
				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		arr=tc.getAllQuestions();
		questionList = FXCollections.observableArrayList();
		
		for(int i=0;i<arr.size();i++)
		{
			ImageView im=new ImageView(new Image("file:C:/Users/chen1/Documents/GitHub/AES-G2/images/questionIcon.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			QuestionGUI qgui=new QuestionGUI(arr.get(i).getQuestionID(),arr.get(i).getTeacherName(),arr.get(i).getQuestionTxt(),im);
			questionList.add(qgui);
		}
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("questionID"));
		teacherName.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("teacherName"));
		QuestionTxt.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("questionTxt"));
		labelCol.setCellValueFactory(new PropertyValueFactory<QuestionGUI,ImageView>("image"));
		table.setItems(questionList);
	}
}
