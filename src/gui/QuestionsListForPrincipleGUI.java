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
import logic.Question;

public class QuestionsListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<Question> table;
	@FXML
	private TableColumn<Question, String> questionID;
	@FXML
	private TableColumn<Question, String> QuestionTxt;
	@FXML
	private TableColumn<Question, String> teacherName;
	private ArrayList<Question> arr;
	// private DatabaseControl dbControl;
	private QuestionDetailsGUI qdg;
	// private Main main;
	ObservableList<Question> questionList;
	ClientConsole client;

	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("QuestionsListForPrinciple.fxml"));
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

	public QuestionsListForPrincipleGUI() {
		client = new ClientConsole();

		pc = new PrincipalController();
		///////// צריך לשנות לקונטרולר של מנהל? מה המתודה הזאת עושה בכלל?
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Show the table:
		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
		QuestionTxt.setCellValueFactory(new PropertyValueFactory<>("QuestionTxt"));
		teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		// fill the table:
		arr = pc.getAllQuestionsInData();
		System.out.println("size of exam array: " + arr.size()); // לצרכי בקרה בלבד
		questionList = FXCollections.observableArrayList();
		questionList.addAll(arr);
		table.setItems(questionList);
	}
}
