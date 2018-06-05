package gui;

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

public class ErrorBoxGUI implements Initializable
{
	@FXML
	Button okButton;
	@FXML
	Button cancleButton;
	@FXML
	Label errorMsgL;
	
	Boolean isOK;
	String errorMsg;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		errorMsgL.setText(errorMsg);
	}
	
	public void start (String msg,Stage primaryStage) throws Exception
	{
		errorMsg=msg;
		Parent root = FXMLLoader.load(getClass().getResource("errorBox.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		
		primaryStage.show(); 
		
	}
	
	public void okButtonAction(ActionEvent ae)
	{
		isOK=true;
		Stage stage = (Stage) okButton.getScene().getWindow();
		stage.close();
	}
	
	public void cancleButtonAction(ActionEvent ae)
	{
		isOK=false;
		Stage stage = (Stage) cancleButton.getScene().getWindow();
		stage.close();
	}

}
