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


public class StatisticsMenuGUI implements Initializable
{
	@FXML
	Button TeacherStatisticsButton;
	@FXML
	Button StudentStatisticsButton;
	@FXML
	Button CourseStatisticsButton;
	@FXML
	Button BackButton;
	private User principle;
	
	public void BackButtonAction(ActionEvent ae) throws Exception
	{
		
	}
	
	public void TeacherStatisticsButtonAction() throws Exception
	{
		InsertTeacherForStatisticsPageGUI qrg=new InsertTeacherForStatisticsPageGUI();
		// this 2 rows: The new window will open instead of the current window.
		Stage st = (Stage)TeacherStatisticsButton.getScene().getWindow();
		qrg.start(st);
	}
	
	public void StudentStatisticsButtonAction() throws Exception
	{
		InsertStudentForStatisticsPageGUI qrg=new InsertStudentForStatisticsPageGUI();
		// this 2 rows: The new window will open instead of the current window.
		Stage st = (Stage)StudentStatisticsButton.getScene().getWindow();
		qrg.start(st);
	}
	
	public void CourseStatisticsButtonAction() throws Exception
	{
		InsertCourseForStatisticsPageGUI qrg=new InsertCourseForStatisticsPageGUI();
		// this 2 rows: The new window will open instead of the current window.
		Stage st = (Stage)CourseStatisticsButton.getScene().getWindow();
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
		Parent root = FXMLLoader.load(getClass().getResource("StatisticsMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
