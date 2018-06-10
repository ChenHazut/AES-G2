
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.LoginController;
import logic.User;


public class StudentMenuGUI implements Initializable
{
	@FXML
	Button PerformanceTestsButton; //THE BUTTON TO DO TEST
	@FXML
	Button ExamScoresButton;//THE BUTTON TO SHOW THE STUDENT SCORES
	
	@FXML
	Label helloMsgLabel; //to show the student name on the label
	private User student; // to save the student that loged in info
	private LoginController lc;
	
	public void PerformanceTestsButtonAction() throws Exception
	{
		PerformanceExamsGUI PG=new PerformanceExamsGUI(); //CREATE THE NEXT WINDOW GUI
		Stage primaryStage=new Stage();
		PG.start(primaryStage); //RUN THE NEW WINDOW GUI
		//Stage stage = (Stage) PerformanceTestsButton.getScene().getWindow(); //close studentmenu window
		//stage.close();
	}
	
	public void ShowExamsScoresAction() throws Exception 
	{
		
		ShowExamsScoresGUI PG=new ShowExamsScoresGUI(); //CREATE THE NEXT WINDOW GUI
		Stage primaryStage=new Stage();
		PG.start(primaryStage); //RUN THE NEW WINDOW GUI
		//Stage stage = (Stage) ExamScoresButton.getScene().getWindow(); //close studentmenu window
		//stage.close();
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		LoginController lc=new LoginController();//save the user detailed
		this.student=lc.getUser(); //save the teacher that connected to the system
		helloMsgLabel.setText("Hello "+student.getuName());
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		lc=new LoginController();
		primaryStage.setOnCloseRequest(event ->{ //LOG OUT THE USER IF HE PRESS "X"
			lc.logoutUser();
		});
	}
}
