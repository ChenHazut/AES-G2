package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.LoginController;
import logic.User;

public class LoginGUI implements Initializable {
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

	public static int port;
	public static String IP;

	String uid;
	String upass;

	private ClientConsole client;
	public ChatClient chat;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		userIDTF.setText("11111");
		passwordTF.setText("1111");

	}

	@FXML
	void Exit(ActionEvent event) {
		System.out.println("exit AES Application");
		System.exit(0);
	}

	// listen to presses on the login button
	public void loginButtonAction(ActionEvent ae) throws Exception {
		// Port
		if (txtPORT.getText().compareTo("") == 0)// empty text field
		{
			port = 5555;
		} else {
			port = Integer.parseInt(txtPORT.getText());
		}

		// IP
		if (txtServerIP.getText().compareTo("") == 0)// empty text field
		{
			IP = "localhost";
		} else {
			IP = txtServerIP.getText();
		}
		client = new ClientConsole(this.IP, this.port);
		uid = userIDTF.getText();
		upass = passwordTF.getText();
		if (uid.equals("") || upass.equals("")) // if one or more of fields are empty
			errorL.setText("details are missing");
		else {
			User userToLog = new User(uid, upass);
			LoginController lc = new LoginController(userToLog);

			if (!lc.checkIfUserIDExist()) // if user id doesn't exist
			{
				System.out.println("id doesn't exist");
				errorL.setText("User ID doesn't exist");
			} else if (!lc.getPassword().equals(upass)) // if password is incorrect
			{
				System.out.println("password is wrong");
				errorL.setText("password is wrong");
			} else if (lc.isConnected()) // if user already connected
			{
				System.out.println("user already logged in");
				errorL.setText("user already logged in");
			} else {
				lc.loginUser();
				((Node) ae.getSource()).getScene().getWindow().hide();
				if (lc.getTitle().equalsIgnoreCase("teacher"))// if user is teacher
				{
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("TeacherMenu.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					TeacherMenuGUI teacherMenu = loader.getController();
					teacherMenu.initData();
					Stage window = new Stage();
					window.setScene(scene);
					window.show();
					window.setOnCloseRequest(event -> {
						lc.logoutUser();
						System.out.println("exit AES Application");
						System.exit(0);
					});
				}

				if (lc.getTitle().equalsIgnoreCase("Student")) // if user is student
				{
					Stage primaryStage = new Stage();
					StudentMenuGUI tmg = new StudentMenuGUI();
					tmg.start(primaryStage);
				}
				if (lc.getTitle().equalsIgnoreCase("Principle")) // if user is principle
				{
					Stage primaryStage = new Stage();
					PrincipleMenuGUI2 tmg = new PrincipleMenuGUI2();
					tmg.start(primaryStage);
				}
				// Stage stage = (Stage) loginButton.getScene().getWindow(); // close login
				// window
				// stage.close();

			}

		}
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();

		primaryStage.setOnCloseRequest(event -> {

			System.out.println("exit AES Application");
			System.exit(0);
		});
	}

}
