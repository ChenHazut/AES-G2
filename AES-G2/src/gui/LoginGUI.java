package gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.LoginController;
import logic.User;

public class LoginGUI implements Initializable
{
	@FXML
	private ImageView logoIV;
	@FXML
	private TextField userIDTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private Button loginButton;
	@FXML
	private Label errorL;
	@FXML
    private ImageView ImageZerli;
	@FXML
	private Button btnExit;
    @FXML
    private TextField txtServerIP;
    @FXML
    private TextField txtPORT;
	String uid;
	String upass;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
	
	  @FXML
	void Exit(ActionEvent event) 
	{
		  System.out.println("exit AES Application");
		  System.exit(0);	
	}
	
	//listen to presses on the login button
	public void loginButtonAction(ActionEvent ae) throws Exception
	{
		
		uid=userIDTF.getText();
		upass=passwordTF.getText();
		if(uid.equals("")||upass.equals("")) //if one or more of fields are empty
			errorL.setText("details are missing");
		else {
			User userToLog= new User(uid,upass);    
			LoginController lc=new LoginController(userToLog);
			
			if(!lc.checkIfUserIDExist())  //if user id doesn't exist
			{
				System.out.println("id doesn't exist");
				errorL.setText("User ID doesn't exist");
			}
			else if(!lc.getPassword().equals(upass)) //if password is incorrect
			{
				System.out.println("password is wrong");
				errorL.setText("password is wrong");
			}
			else if(lc.isConnected())  //if user already connected
			{
				System.out.println("user already logged in");
				errorL.setText("user already logged in");
			}
			else 
			{
				lc.loginUser();

				if(lc.getTitle().equalsIgnoreCase("teacher"))//if user is teacher
				{
					Stage primaryStage=new Stage();
					TeacherMenuGUI tmg= new TeacherMenuGUI();
					tmg.start(primaryStage);
				}
				Stage stage = (Stage) loginButton.getScene().getWindow(); //close login window
				stage.close();
			}
			
		}
		
		
	}
	
	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
	
	
}
