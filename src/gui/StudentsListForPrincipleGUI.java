package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import logic.PrincipalController;
import logic.User;

public class StudentsListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<UserRow> table; // Student is a kind of user.
	@FXML
	private TableColumn<UserRow, String> id;
	@FXML
	private TableColumn<UserRow, String> name;

	private ArrayList<User> arr;

	private QuestionDetailsGUI qdg;

	ObservableList<UserRow> StudentsList;
	ClientConsole client;

	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("StudentsListForPrinciple.fxml"));
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

	public StudentsListForPrincipleGUI() {
		client = new ClientConsole();
		pc = new PrincipalController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("fhefhkjdhvjszdnwe");
		// Show the table:
		id.setCellValueFactory(new PropertyValueFactory<UserRow, String>("userID"));
		name.setCellValueFactory(new PropertyValueFactory<UserRow, String>("userName"));
		// fill the table:
		arr = pc.getAllStudentsInData();
		ArrayList<UserRow> userArr = new ArrayList<UserRow>();
		for (User u : arr) {
			UserRow user = new UserRow(u, null);
			userArr.add(user);
		}

		System.out.println("size of students array: " + arr.size()); // לצרכי בקרה בלבד
		StudentsList = FXCollections.observableArrayList();
		StudentsList.addAll(userArr);
		System.out.println("finish take all students");
		table.setItems(StudentsList);

	}
}
