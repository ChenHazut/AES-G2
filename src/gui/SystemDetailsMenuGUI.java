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


public class SystemDetailsMenuGUI implements Initializable
{
	@FXML
	Button ExamsListButton;
	@FXML
	Button QuestionsListButton;
	@FXML
	Button SudentsListButton;
	@FXML
	Button TeachersListButton;
	@FXML
	Button CoursesListButton;
	@FXML
	Button SubjectsListButton;
	private User principle;
	
	public void ExamsListButtonAction()
	{
		
	}
	
	public void QuestionsListButtonAction()
	{
		
	}
	
	public void SudentsListButtonAction()
	{
		
	}
	
	public void TeachersListButtonAction()
	{
		
	}
	
	public void CoursesListButtonAction()
	{
		
	}
	
	public void SubjectsListButtonAction()
	{
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{/*
		LoginController lc=new LoginController();
		this.principle=lc.getUser();
		helloMsgLabel_P.setText("Hello "+principle.getuName()+",");
		//now, after the initialization, the title is "Hello XXX YYY,"
	*/}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("SystemDetailsMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
