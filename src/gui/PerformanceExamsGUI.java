package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import logic.LoginController;
import logic.Question;
import logic.User;

public class PerformanceExamsGUI implements Initializable{

	@FXML
	Button ManuallyButton; //THE BUTTON TO DO TEST ManuallyButton
	@FXML
	Button ComputerizedButton;//THE BUTTON TO DO TEST ComputerizedButton
	//@FXML
	//Button MainMenuButton; //THE BUTTON that go back to the main menu
	@FXML
	private TableView table;// THE TABLE THAT SHOW THE EXAMS THAT THE STUDENT CAN DO
	@FXML
	private TableColumn <Question,String> ExamNumber;
	@FXML
	private TableColumn <Question,String> CourseName;
	@FXML
	private TableColumn <Question,String> TeacherName;
	
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
	
	/*public void MainMenuButtonAction() throws Exception //open back the main menu
	{
		Stage primaryStage=new Stage();
		StudentMenuGUI tmg= new StudentMenuGUI(); //to open the student menu
		tmg.start(primaryStage);
		Stage stage = (Stage) MainMenuButton.getScene().getWindow(); //close studentmenu window
		stage.close();
	}*/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		
		ExamNumber.setCellValueFactory(new PropertyValueFactory<>("ExamNumber"));
		CourseName.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
		TeacherName.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
	//	arr=tc.getAllQuestions();
	//	questionList = FXCollections.observableArrayList();
	//	questionList.addAll(arr);
	//	table.setItems(questionList);
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("PerformanceExamsStudent.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}
}
