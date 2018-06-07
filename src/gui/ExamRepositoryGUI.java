package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Course;
import logic.Question;
import logic.TeacherController;
import logic.Exam;

public class ExamRepositoryGUI implements Initializable
{
	@FXML
	private TableView table;
	@FXML
	private TableColumn <Exam,String> ExamIDCol;
	@FXML
	private TableColumn <Exam,String> courseNameCol;
	@FXML
	private TableColumn <Exam,String> teacherNameCol;
	@FXML
	private TableColumn <Exam,String> duration;
	@FXML
	private Button editButton;
	private ArrayList<Exam> arr;
	private ExamDetailsGUI tdg;
	ObservableList<Exam> examList ;
	ClientConsole client;
	GUImanager m;
	TeacherController tc;
	
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("ExamRepository.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show(); 
	}
	
	public void insertButtonAction(ActionEvent ae) throws IOException
	{
		System.out.println("Exam is added");
		Stage stage = (Stage) editButton.getScene().getWindow();
		NewExamGUI ntg=new NewExamGUI();
		ntg.start(stage);
	}
	
	public void deleteExamButtonAction(ActionEvent ae)
	{
		System.out.println("Exam is deleted");
		Exam tToDel=(Exam) table.getSelectionModel().getSelectedItem();
		if(tToDel==null)
			return;
		tc.deleteExam(tToDel);
		for(int i=0;i<examList.size();i++)
			if(examList.get(i).getExamID().equals(tToDel.getExamID()))
			{
				examList.remove(i);
				break;
			}
	}
	
	public void editExamButtonAction(ActionEvent ae) throws Exception
	{
		Exam t=(Exam) table.getSelectionModel().getSelectedItem();
		if(t==null)
			return;
		Stage stage = (Stage) editButton.getScene().getWindow();
		m.selectedExam=t;
		tdg = new ExamDetailsGUI();
		tdg.start(stage);
		System.out.println("Exam is changed");
	}
	
	public ExamRepositoryGUI()
	{
		client= new ClientConsole();
		m=new GUImanager();
		tc=new TeacherController();
				
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ExamIDCol.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		courseNameCol.setCellValueFactory(new PropertyValueFactory<>("course"));
		teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		arr=tc.getAllExams();
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);
	}
}
