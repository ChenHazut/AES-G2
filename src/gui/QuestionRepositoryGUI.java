package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class QuestionRepositoryGUI implements Initializable
{
	@FXML
	private TableView table;
	@FXML
	private TableColumn <Question,String> questionID;
	@FXML
	private TableColumn <Question,String> QuestionTxt;
	@FXML
	private TableColumn <Question,String> teacherName;
	@FXML
	private Button editQuestionButton;
	private ArrayList<Question> arr;
	//private DatabaseControl dbControl;
	private QuestionDetailsGUI qdg;
	//private Main main;
	ObservableList<Question> questionList ;
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
		Stage stage = (Stage) editQuestionButton.getScene().getWindow();
		NewQuestionGUI nqg=new NewQuestionGUI();
		nqg.start(stage);
	}
	
	public void deleteQuestionButtonAction(ActionEvent ae)
	{
		System.out.println("question is deleted");
		Question qToDel=(Question) table.getSelectionModel().getSelectedItem();
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
		Question q=(Question) table.getSelectionModel().getSelectedItem();
		if(q==null)
			return;
		Stage stage = (Stage) editQuestionButton.getScene().getWindow();
		m.selectedQuestion=q;
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
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		QuestionTxt.setCellValueFactory(new PropertyValueFactory<>("QuestionTxt"));
		teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		arr=tc.getAllQuestions();
		questionList = FXCollections.observableArrayList();
		questionList.addAll(arr);
		table.setItems(questionList);
	}
}
