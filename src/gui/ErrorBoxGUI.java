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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorBoxGUI implements Initializable
{
	@FXML
	Button okButton;
	@FXML
	Button cancleButton;
	@FXML
	Text text;
	
	private Boolean result;
	String errorMsg;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
	
	public void okButtonAction(ActionEvent ae)
	{
		result=true;
		Stage s=(Stage) okButton.getScene().getWindow();
		System.out.println("ok is pressed");
		s.close();
	}
	
	public void cancleButtonAction(ActionEvent ae)
	{
		result=false;
		Stage s=(Stage) cancleButton.getScene().getWindow();
		System.out.println("cancle is pressed");
		s.close();
	}



	public void initData(String stringMsg) {
		text.setText(stringMsg);
		
	}

	public Boolean getResult() {
		return result;
	}
	
	

}
