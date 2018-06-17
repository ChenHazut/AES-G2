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
import logic.User;


public class SystemDetailsMenuGUI implements Initializable
{
	@FXML
	Button ExamsListButton;
	@FXML
	Button QuestionsListButton;
	@FXML
	Button StudentsListButton;
	@FXML
	Button TeachersListButton;
	@FXML
	Button CoursesListButton;
	@FXML
	Button SubjectsListButton;
	@FXML
	Button BackButton;
	private User principle;
	
	public void BackButtonAction(ActionEvent ae) throws Exception
	{
		PrincipleMenuGUI2 qrg=new PrincipleMenuGUI2();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)BackButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void ExamsListButtonAction() throws Exception
	{
		ExamsListForPrincipleGUI qrg=new ExamsListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)ExamsListButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void QuestionsListButtonAction() throws Exception
	{
		QuestionsListForPrincipleGUI qrg=new QuestionsListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)QuestionsListButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void StudentsListButtonAction() throws Exception
	{
		StudentsListForPrincipleGUI qrg=new StudentsListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)StudentsListButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void TeachersListButtonAction() throws Exception
	{
		TeachersListForPrincipleGUI qrg=new TeachersListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)TeachersListButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void CoursesListButtonAction() throws Exception
	{
		CoursesListForPrincipleGUI qrg=new CoursesListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)CoursesListButton.getScene().getWindow();
				qrg.start(st);
	}
	
	public void SubjectsListButtonAction() throws Exception
	{
		SubjectsListForPrincipleGUI qrg=new SubjectsListForPrincipleGUI();
		// this 2 rows: The new window will open instead of the current window.
				Stage st = (Stage)SubjectsListButton.getScene().getWindow();
				qrg.start(st);
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
