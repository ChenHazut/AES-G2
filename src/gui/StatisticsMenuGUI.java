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


public class StatisticsMenuGUI implements Initializable
{
	@FXML
	Button TeacherStatisticsButton;
	@FXML
	Button StudentStatisticsButton;
	@FXML
	Button CourseStatisticsButton;
	private User principle;
	
	public void TeacherStatisticsButtonAction()
	{
		
	}
	
	public void StudentStatisticsButtonAction()
	{
		
	}
	
	public void CourseStatisticsButtonAction()
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
		Parent root = FXMLLoader.load(getClass().getResource("StatisticsMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
