package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import logic.LoginController;
import logic.User;

public class ShowExamsScoresGUI implements Initializable {
	@FXML
	Button ShowExamButton; //THE BUTTON that show the exam of the student
	@FXML
	ComboBox ComoSubject; //combox to the subjects
	@FXML
	ComboBox Course; //combox to the courses
	@FXML
	TextField score; //the text to show the score in the exam
	
	private User student; // to save the student that loged in info
	
	public void PerformanceTestsButtonAction() throws Exception
	{
		PerformanceExamsGUI PG=new PerformanceExamsGUI(); //CREATE THE NEXT WINDOW GUI
		Stage primaryStage=new Stage();
		PG.start(primaryStage); //RUN THE NEW WINDOW GUI
	}
	
	public void ShowExamButtonAction() throws Exception
	{
		//need to show the student the checked exam
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		//here need to set the combox
		LoginController lc=new LoginController();//save the user detailed
		this.student=lc.getUser(); //save the teacher that connected to the system
		
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
