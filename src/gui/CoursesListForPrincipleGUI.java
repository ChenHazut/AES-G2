package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Course;
import logic.PrincipalController;

public class CoursesListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<Course> table;
	@FXML
	private TableColumn<Course, String> cID;
	@FXML
	private TableColumn<Course, String> cName;

	private ArrayList<Course> arr;

	// private QuestionDetailsGUI qdg;

	ObservableList<Course> courseList;
	ClientConsole client;

	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("CoursesListForPrinciple.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}

	public void BackButtonAction(ActionEvent ae) throws Exception {
		SystemDetailsMenuGUI qrg = new SystemDetailsMenuGUI();
		// this 2 rows: The new window will open instead of the current window.
		Stage st = (Stage) BackButton.getScene().getWindow();
		qrg.start(st);
	}

	public CoursesListForPrincipleGUI() {
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		pc = new PrincipalController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cID.setCellValueFactory(new PropertyValueFactory<>("cID"));
		cName.setCellValueFactory(new PropertyValueFactory<>("cName"));

	}
}
