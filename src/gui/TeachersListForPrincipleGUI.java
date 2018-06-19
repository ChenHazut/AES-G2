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
import logic.PrincipalController;
import logic.User;

public class TeachersListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<UserRow> table; // Teacher is a kind of user.
	@FXML
	private TableColumn<UserRow, String> uID;
	@FXML
	private TableColumn<UserRow, String> uName;

	private ArrayList<User> arr;

	private QuestionDetailsGUI qdg;

	private ObservableList<UserRow> TeachersList;
	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TeachersListForPrinciple.fxml"));
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

	public TeachersListForPrincipleGUI() {
		pc = new PrincipalController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		uID.setCellValueFactory(new PropertyValueFactory<UserRow, String>("userID"));
		uName.setCellValueFactory(new PropertyValueFactory<UserRow, String>("userName"));

		arr = pc.getAllTeachersInData();
		ArrayList<UserRow> userArr = new ArrayList<UserRow>();
		for (User u : arr) {
			UserRow user = new UserRow(u, null);
			userArr.add(user);
		}

		TeachersList = FXCollections.observableArrayList();
		TeachersList.addAll(userArr);
		System.out.println("finish take all teachers");
		table.setItems(TeachersList);
	}
}
