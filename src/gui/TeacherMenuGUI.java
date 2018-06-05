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
	private User teacher;
	private LoginController lc;
	
	public void questionRepositoryButtonAction() throws Exception
	{
		QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void testRepositoryButtonAction()
	{
		
	}
	
	public void testInExecutionButtonAction()
	{
		
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
		});
	}
}
