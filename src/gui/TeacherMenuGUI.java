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
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        QuestionRepositoryGUI qrg=loader.getController();
        qrg.initData();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();

	}
	
	public void testRepositoryButtonAction(ActionEvent ae) throws Exception
	{

	}
	
	public void testInExecutionButtonAction(ActionEvent ae) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("examInExecutionMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ExamInExecutionMenuGUI examInExecMenu=loader.getController();
        examInExecMenu.initData();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
	}
	
	public void reportButtonAction(ActionEvent ae) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("reportMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ReportMenuGUI reportMenu=loader.getController();
        reportMenu.initData();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
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
		//Stage stage=(Stage) testRepositoryButton.getScene().getWindow();

		
	}


	public void initData() {

		LoginController lc=new LoginController();
		this.teacher=lc.getUser();
		helloMsgLabel.setText("Hello "+teacher.getuName()+",");

		
	}
}
