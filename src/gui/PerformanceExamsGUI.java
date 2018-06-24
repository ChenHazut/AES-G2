package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
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
import logic.User;

public class PerformanceExamsGUI implements Initializable {

	@FXML
	Button ManuallyButton; // THE BUTTON TO DO TEST ManuallyButton
	@FXML
	Button ComputerizedButton;// THE BUTTON TO DO TEST ComputerizedButton
	@FXML
	private TableView<ExamInExecution> table;// THE TABLE THAT SHOW THE EXAMS THAT THE STUDENT CAN DO
	@FXML
	private TableColumn<ExamInExecution, String> examIDCol;
	@FXML
	private TableColumn<ExamInExecution, String> cNameCol;
	@FXML
	private TableColumn<ExamInExecution, String> teacherNameCol;
	@FXML
	private TableColumn<ExamInExecution, String> durationCol;
	private ArrayList<ExamInExecution> arr;
	ObservableList<ExamInExecution> examList;
	StudentController st;

	private User student; // to save the student that loged in info

	public void ManuallyButtonButtonAction() throws Exception {
		ExamInExecution e = table.getSelectionModel().getSelectedItem();
		if (e == null)
			return;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ManuallyExam.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ManuallyExamGUI manual = loader.getController();
		manual.initData(e);
		Stage window = (Stage) ManuallyButton.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	public void ComputerizedButtonAction() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ComputerizadExam.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ComputerizedExamGUI ceg = loader.getController();
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		ceg.initData(table.getSelectionModel().getSelectedItem());
		Stage window = (Stage) ManuallyButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		st = new StudentController();
		examIDCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getExamDet().getExamID()));
		cNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		teacherNameCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getExecTeacher().getuName()));

		durationCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getExamDet().getDuration())));
		arr = st.getAllExamsInExecutin();

		System.out.println("size of exam array: " + arr.size());
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StudentExamToPerform.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();

	}
}
