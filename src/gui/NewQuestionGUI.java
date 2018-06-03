package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;

public class NewQuestionGUI extends QuestionForm 
{
	
	//Question q;
	
	public NewQuestionGUI() 
	{
		super();
		
		
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		System.out.println("bla bla bla bla");
		Parent root = FXMLLoader.load(getClass().getResource("QuestionDetails.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("1234567890");
	}



	
}
