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
import logic.TeacherController;

public class ExamRepositoryGUI implements Initializable
{
	@FXML
	private TableView<Exam> table;
	@FXML
	private TableColumn <Exam,String> examIDCol;
	@FXML
	private TableColumn <Exam,String> cNameCol;
	@FXML
	private TableColumn <Exam,String> teacherNameCol;
	@FXML
	private TableColumn <Exam,String> durationCol;
	@FXML
	private Button editButton;
	@FXML
	private Button insertButton;
	
	
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
	
	public void insertButtonAction(ActionEvent ae) throws Exception
	{
		System.out.println("Exam is added");
		Stage stage = (Stage) insertButton.getScene().getWindow();
		CreateExamGUI ntg=new CreateExamGUI();
		ntg.start(stage);
	}
	
	public void deleteButtonAction(ActionEvent ae)
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
	
	public void editButtonAction(ActionEvent ae) throws Exception
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
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		cNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		arr=tc.getAllExams();
		System.out.println("size of exam array: "+arr.size());
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);
	}
}
