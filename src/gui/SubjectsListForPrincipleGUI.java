package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.security.auth.Subject;

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

public class SubjectsListForPrincipleGUI implements Initializable {
	@FXML
	Button BackButton;
	@FXML
	private TableView<Subject> table;
	@FXML
	private TableColumn<Subject, String> subjectID;
	@FXML
	private TableColumn<Subject, String> sName;

	private ArrayList<Subject> arr;

	private QuestionDetailsGUI qdg;

	ObservableList<Subject> subjectsList;
	PrincipalController pc;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SubjectsListForPrinciple.fxml"));
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

	public SubjectsListForPrincipleGUI() {
		pc = new PrincipalController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		subjectID.setCellValueFactory(new PropertyValueFactory<>("subjectID"));
		sName.setCellValueFactory(new PropertyValueFactory<>("sName"));
	}
}
