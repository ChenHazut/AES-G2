package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.LoginController;
import logic.TeacherController;
import logic.User;


public class TeacherMenuGUI implements Initializable
{
	@FXML
	Button testRepositoryButton;
	@FXML
	Button questionRepositoryButton;
	@FXML
	Button testInExecutionButton;
	@FXML
	Label helloMsgLabel;
	@FXML
	Button logoutButton;
	private User teacher;
	private LoginController lc;
	
	public void questionRepositoryButtonAction(ActionEvent ae) throws Exception
	{
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void testRepositoryButtonAction(ActionEvent ae) throws Exception
	{
		ExamRepositoryGUI erg=new ExamRepositoryGUI();
		Stage primaryStage=new Stage();
		erg.start(primaryStage);
	}
	
	public void testInExecutionButtonAction(ActionEvent ae) throws IOException
	{
		ExamInExecutionMenuGUI eInExecutionMg=new ExamInExecutionMenuGUI();
		Stage primaryStage=new Stage();
		eInExecutionMg.start(primaryStage);
	}
	
	public void reportButtonAction(ActionEvent ae) throws Exception
	{
		ReportMenuGUI rmg=new ReportMenuGUI();
		Stage primaryStage=new Stage();
		rmg.start(primaryStage);
	}
	
	public void logoutButtonAction(ActionEvent ae) throws IOException
	{
		lc=new LoginController();
		lc.logoutUser();
		Stage stage=(Stage) logoutButton.getScene().getWindow();
		LoginGUI lg = new LoginGUI();   //run login window
		lg.start(stage);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		LoginController lc=new LoginController();
		this.teacher=lc.getUser();
		helloMsgLabel.setText("Hello "+teacher.getuName()+",");
		
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("teacherMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		lc=new LoginController();
		primaryStage.setOnCloseRequest(event ->{
			lc.logoutUser();
			System.out.println("exit AES Application");
			System.exit(0);	
		});
	}
}
