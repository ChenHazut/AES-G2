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


public class PrincipleMenuGUI2 implements Initializable
{
	@FXML
	Button ExtraTimeRequestsButton;
	@FXML
	Button StatisticsButton;
	@FXML
	Button SystemDetailsButton;
	@FXML
	Label helloMsgLabel_P;
	private User principle;
	private LoginController lc;
	
	public void ExtraTimeRequestsButtonAction() throws Exception
	{
		
	}
	
	public void StatisticsButtonAction() throws Exception
	{ // When we click on the Statistics button, it will send us to the Statistics menu.
		StatisticsMenuGUI qrg=new StatisticsMenuGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	public void SystemDetailsButtonAction() throws Exception
	{
		// When we click on the System Details button, it will send us to the System Details menu.
		SystemDetailsMenuGUI qrg=new SystemDetailsMenuGUI();
		Stage primaryStage=new Stage();
		qrg.start(primaryStage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		LoginController lc=new LoginController();
		this.principle=lc.getUser();
		helloMsgLabel_P.setText("Hello "+principle.getuName()+",");
		//now, after the initialization, the title is "Hello XXX YYY,"
	}

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("principleMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		// from this line the code logs out the user when he press on X button:
		lc=new LoginController();
		primaryStage.setOnCloseRequest(event ->{
			lc.logoutUser();
		});
	}
}
