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
import logic.Exam;
import logic.PrincipalController;

public class ExamsListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<Exam> table;
	@FXML
	private TableColumn<Exam, String> ExamID;
	@FXML
	private TableColumn<Exam, String> courseName;
	@FXML
	private TableColumn<Exam, String> teacherName;
	private ArrayList<Exam> arr;

	ObservableList<Exam> examList;
	ClientConsole client;
	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ExamsListForPrinciple.fxml"));
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

	public ExamsListForPrincipleGUI() {
		client = new ClientConsole();
		pc = new PrincipalController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Show the table:
		ExamID.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		// fill the table:
		arr = pc.getAllExamsInData();
		System.out.println("size of exam array: " + arr.size()); // לצרכי בקרה בלבד
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);
	}
}
