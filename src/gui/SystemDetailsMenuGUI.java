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
	Button BeckButton;
	private User principle;
	
	public void BeckButtonAction(ActionEvent ae) throws Exception
	{
		PrincipleMenuGUI2 qrg=new PrincipleMenuGUI2();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void ExamsListButtonAction() throws Exception
	{
		ExamsListForPrincipleGUI qrg=new ExamsListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void QuestionsListButtonAction() throws Exception
	{
		QuestionsListForPrincipleGUI qrg=new QuestionsListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void StudentsListButtonAction() throws Exception
	{
		StudentsListForPrincipleGUI qrg=new StudentsListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void TeachersListButtonAction() throws Exception
	{
		TeachersListForPrincipleGUI qrg=new TeachersListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void CoursesListButtonAction() throws Exception
	{
		CoursesListForPrincipleGUI qrg=new CoursesListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void SubjectsListButtonAction() throws Exception
	{
		SubjectsListForPrincipleGUI qrg=new SubjectsListForPrincipleGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
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
