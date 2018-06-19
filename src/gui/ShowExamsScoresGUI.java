package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import logic.ExamInExecution;
import logic.StudentController;
import logic.StudentInExam;
import logic.User;

public class ShowExamsScoresGUI implements Initializable {
	@FXML
	Button ShowExamButton; // THE BUTTON that show the exam of the student
	// @FXML
	// Button MainMenuButton; //THE BUTTON that go back to the main menu

	@FXML
	private TableView<StudentInExam> gradeTable;
	@FXML
	private TableColumn<StudentInExam, String> examID;
	@FXML
	private TableColumn<StudentInExam, Integer> grade;
	@FXML
	private TableColumn<StudentInExam, Timestamp> dateCol;
	@FXML
	private TableColumn<StudentInExam, String> courseName;

	private User student; // to save the student that loged in info

	private ArrayList<StudentInExam> arr; // for all the grades of the student

	StudentController st;

	ObservableList<StudentInExam> GradesList;

	public void ShowExamButtonAction() throws Exception {
		ExamInExecution exam = st.getExamForStudent(gradeTable.getSelectionModel().getSelectedItem());

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamFormForStudent.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamFormForStudentGUI ExamForStudent = loader.getController();
		ExamForStudent.initData(exam, false);
		Stage stage = (Stage) gradeTable.getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		st = new StudentController();
		examID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

		arr = st.getAllgrades(); // save all the student grades in arr

		GradesList = FXCollections.observableArrayList();
		GradesList.addAll(arr);
		gradeTable.setItems(GradesList);
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowExamsScores.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
