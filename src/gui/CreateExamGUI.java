package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class CreateExamGUI implements Initializable {
	
	@FXML
	private TableView table;
	@FXML
	private TableColumn <Question,String> questionID;
	@FXML
	private TableColumn <Question,String> questionText;
	@FXML
	private TableColumn <Question,String> author;
	
	private ArrayList<Question> questionArr;
	ObservableList<Question> questionsList ;
	ClientConsole client;
	GUImanager m;
	TeacherController tc;
	
	public CreateExamGUI()
	{
		client= new ClientConsole();
		m=new GUImanager();
		tc=new TeacherController();
				
	}
	
	
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("CreateExam.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show(); 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		questionText.setCellValueFactory(new PropertyValueFactory<>("QuestionTxt"));
		author.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		questionArr=tc.getAllQuestions();
		questionsList = FXCollections.observableArrayList();
		questionsList.addAll(questionArr);
		table.setItems(questionsList);
		
		
	}
}
