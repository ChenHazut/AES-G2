//package gui;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import client.ChatClient;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.*;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.SelectionMode;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TreeTableColumn;
//import javafx.scene.control.TreeTableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import logic.ClientConsole;
//import logic.Question;
//import logic.TeacherController;
//
//public class TeachersListForPrincipleGUI implements Initializable
//{
//	@FXML
//	Button BeckButton;
//	@FXML
//	private TableView table;
//	@FXML
//	private TableColumn <Question,String> questionID;
//	@FXML
//	private TableColumn <Question,String> QuestionTxt;
//	@FXML
//	private TableColumn <Question,String> teacherName;
//	private ArrayList<Question> arr;
//	//private DatabaseControl dbControl;
//	private QuestionDetailsGUI qdg;
//	//private Main main;
//	ObservableList<Question> questionList ;
//	ClientConsole client;
//	GUImanager m;
//	TeacherController tc;
//	
//	/**
//	 * 
//	 * @param primaryStage
//	 * @throws Exception
//	 */
//	public void start(Stage primaryStage) throws Exception
//	{
//		Parent root = FXMLLoader.load(getClass().getResource("TeachersListForPrinciple.fxml"));
//		Scene Scene = new Scene(root);
//		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(Scene);
//		primaryStage.show(); 
//	}
//	
//	public void BeckButtonAction(ActionEvent ae) throws Exception
//	{
//		StatisticsMenuGUI qrg=new StatisticsMenuGUI();
//		Stage primaryStage=new Stage();
//		qrg.start(primaryStage);
//	}
//	
//	
//	public TeachersListForPrincipleGUI()
//	{
//		client= new ClientConsole();
//		m=new GUImanager();
//		tc=new TeacherController();
//		///////// צריך לשנות לקונטרולר של מנהל? מה המתודה הזאת עושה בכלל?		
//	}
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		questionID.setCellValueFactory(new PropertyValueFactory<>("questionID"));
//		QuestionTxt.setCellValueFactory(new PropertyValueFactory<>("QuestionTxt"));
//		teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
//		arr=tc.getAllQuestions();
//		questionList = FXCollections.observableArrayList();
//		questionList.addAll(arr);
//		table.setItems(questionList);
//	}
//}
