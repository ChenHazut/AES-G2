package gui;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Exam;
import logic.ExamInExecution;
import logic.LoginController;
import logic.Question;
import logic.StudentController;
import logic.User;

public class PerformanceExamsGUI implements Initializable{

	@FXML
	Button ManuallyButton; //THE BUTTON TO DO TEST ManuallyButton
	@FXML
	Button ComputerizedButton;//THE BUTTON TO DO TEST ComputerizedButton
	@FXML
	private TableView table;// THE TABLE THAT SHOW THE EXAMS THAT THE STUDENT CAN DO
	@FXML
	private TableColumn <Exam,String> examIDCol;
	@FXML
	private TableColumn <Exam,String> cNameCol;
	@FXML
	private TableColumn <Exam,String> teacherNameCol;
	@FXML
	private TableColumn <Exam,String> durationCol;
	private ArrayList<Exam> arr;
	ObservableList<Exam> examList ;
	StudentController st;
	
	private User student; // to save the student that loged in info
	
	
	public void ManuallyButtonButtonAction() throws Exception
	{
		//NewQuestionGUI nqg=new NewQuestionGUI();
		//nqg.start(stage);
	}
	
	public void ComputerizedButtonAction() throws Exception
	{
		//NewQuestionGUI nqg=new NewQuestionGUI();
		//nqg.start(stage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		st = new StudentController();
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		cNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		arr=st.getAllExamsInExecutin();
		System.out.println("size of exam array: "+arr.size());
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("StudentExamToPerform.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}
}
