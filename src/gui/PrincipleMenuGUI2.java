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
import logic.ClientConsole;
import logic.LoginController;
import logic.User;

public class PrincipleMenuGUI2 implements Initializable {
	@FXML
	Button ExtraTimeRequestsButton;
	@FXML
	Button StatisticsButton;
	@FXML
	Button SystemDetailsButton;
	@FXML
	Label helloMsgLabel_P;
	@FXML
	private Label notification;
	@FXML
	private Button viewRequests;
	@FXML
	Button logoutButton;
	private User principal;
	private LoginController lc;
	private boolean flag = false;
	private Thread overtimeThread;
	private ClientConsole client;

	public void ExtraTimeRequestsButtonAction() throws Exception {
		flag = false;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OvertimeRequestMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		OvertimeRequestMenuGUI rot = loader.getController();
		rot.initData();
		Stage window = new Stage();
		window.setScene(scene);
		window.show();
	}

	public void StatisticsButtonAction() throws Exception { // When we click on the Statistics button, it will send us
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("StatisticsMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		StatisticsMenuGUI sm = loader.getController();
		sm.initData(principal);
		Stage window = new Stage();
		window.setScene(scene);
		window.show();
	}

	public void SystemDetailsButtonAction() throws Exception {
		// When we click on the System Details button, it will send us to the System
		// Details menu.
		SystemDetailsMenuGUI qrg = new SystemDetailsMenuGUI();
		Stage primaryStage = new Stage();
		qrg.start(primaryStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		LoginController lc = new LoginController();
		lc.getUser().setTitle("Principal");
		this.principal = lc.getUser();
		helloMsgLabel_P.setText("Hello " + principal.getuName() + ",");
		// now, after the initialization, the title is "Hello XXX YYY,"
		overtimeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.getMessage().getReturnObj() instanceof String) {
						if (((String) client.getMessage().getReturnObj()).equals("newOverTimeRequest")
								&& flag == false) {
							notification.setVisible(true);
							viewRequests.setVisible(true);
							flag = true;

							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							notification.setVisible(false);
							viewRequests.setVisible(false);

						}
					}
				}

			}

		});
		overtimeThread.start();
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("principleMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		// from this line the code logs out the user when he press on X button:
		lc = new LoginController();
		primaryStage.setOnCloseRequest(event -> {
			lc.logoutUser();
			System.exit(0);
		});
	}

	public void logoutButtonAction(ActionEvent ae) throws IOException {
		lc = new LoginController();
		lc.logoutUser();
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		LoginGUI lg = new LoginGUI(); // run login window
		lg.start(stage);

	}
}
