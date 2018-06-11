package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class CreateExamGUI implements Initializable {
	
	@FXML
	private TableView<QuestionGUI> table;
	@FXML
	private TableColumn <QuestionGUI,String> questionID;
	@FXML
	private TableColumn <QuestionGUI,String> questionText;
	@FXML
	private TableColumn <QuestionGUI,String> author;
	@FXML
	private TableColumn <QuestionGUI,CheckBox> selected;
	
	private ArrayList<Question> questionArr;
	ObservableList<QuestionGUI> questionsList ;
	ClientConsole client;
	GUImanager m;
	TeacherController tc;
	
	public CreateExamGUI()
	{
		client= new ClientConsole();
		m=new GUImanager();
		tc=new TeacherController();
				
	}
	
	
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("CreateExam.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show(); 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("questionID"));
		questionText.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("questionTxt"));
		author.setCellValueFactory(new PropertyValueFactory<QuestionGUI,String>("teacherName"));
		selected.setCellValueFactory(new PropertyValueFactory<QuestionGUI,CheckBox>("checkButton"));
		questionArr=tc.getAllQuestions();
		questionsList = FXCollections.observableArrayList();
		for(int i=0;i<questionArr.size();i++)
		{
			CheckBox cb=new CheckBox();
			cb.setVisible(true);
			QuestionGUI qgui=new QuestionGUI(questionArr.get(i).getQuestionID(),questionArr.get(i).getTeacherName(),
					questionArr.get(i).getQuestionTxt(),cb);
			questionsList.add(qgui);
		}
		
		table.setItems(questionsList);
				
	}
}
