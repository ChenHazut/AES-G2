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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.LoginController;
import logic.Question;
import logic.StudentController;
import logic.StudentInExam;
import logic.TeacherController;
import logic.User;

public class ShowExamsScoresGUI implements Initializable {
	@FXML
	Button ShowExamButton; //THE BUTTON that show the exam of the student
	//@FXML
	//Button MainMenuButton; //THE BUTTON that go back to the main menu
	
	@FXML
	private TableView gradeTable;
	@FXML
	private TableColumn <StudentInExam,String> examID; 
	//@FXML
//	private TableColumn <StudentInExam,String> examDate;
	@FXML
	private TableColumn <StudentInExam,Integer> grade;
	
	private User student; // to save the student that loged in info
	
	private ArrayList<StudentInExam> arr; //for all the grades of the student 
	
	StudentController st;
	
	ObservableList<StudentInExam> GradesList ;
	
	public void ShowExamButtonAction() throws Exception
	{
		//need to show the student the checked exam
		
	}
	
	/*public void MainMenuButtonAction() throws Exception //open back the main menu
	{
		Stage primaryStage=new Stage();
		StudentMenuGUI tmg= new StudentMenuGUI(); //to open the student menu
		tmg.start(primaryStage);
		Stage stage = (Stage) MainMenuButton.getScene().getWindow(); //close window
		stage.close();
	}*/
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		st=new StudentController();
		examID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		//examDate.setCellValueFactory(new PropertyValueFactory<StudentInExam,String>("Exam Date"));
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
//		
		arr=st.getAllgrades(); //save all the student grades in arr
//		
		GradesList = FXCollections.observableArrayList();
		if(arr!=null)
			GradesList.addAll(arr);
		gradeTable.setItems(GradesList);
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("ShowExamsScores.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
