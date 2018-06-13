package gui;

import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.TeacherController;

public class ExamInExecutionMenuGUI implements Initializable {

    @FXML
    private TableView<ExamInExecutionRow> examsTable;

    @FXML
    private TableColumn<ExamInExecutionRow, ImageView> imageCol;
    
    @FXML
    private TableColumn<ExamInExecutionRow, String> examIDCol;

    @FXML
    private TableColumn<ExamInExecutionRow, String> executionIDCol;

    @FXML
    private TableColumn<ExamInExecutionRow, String> courseNameCol;

    @FXML
    private Button executeNewExamBtn;

    @FXML
    private Button approveGradesViewBtn;

    private TeacherController tc;
    
    private ObservableList<ExamInExecutionRow> examArr;
    
    private ArrayList<ExamInExecution>arr;
    
    public ExamInExecutionMenuGUI()
    {
    	tc=new TeacherController();
    	examArr= FXCollections.observableArrayList();
    	arr=tc.getAllExamsInExecutionForTeacher();
    	
    	
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) 
    {
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDCol.setCellValueFactory(new PropertyValueFactory<>("executionID") );
		courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageCol.setCellValueFactory(new PropertyValueFactory<>("preview"));
		int i;
		for(i=0;i<arr.size();i++)
    	{
    		ExamInExecutionRow e=new ExamInExecutionRow();
    		e.setExamID(arr.get(i).getExamDet().getExamID());
    		e.setExecutionID(arr.get(i).getExecutionID());
    		e.setCourseName(arr.get(i).getCourseName());
    		ImageView im=new ImageView(new Image("/images/preview.png"));
    		im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
    		e.setPreview(im);
    		System.out.println("*** "+arr.get(i).getExamDet().getExamID());
    		examArr.add(e);
    	}
		examsTable.setItems(examArr);
		
	}
    


	@FXML
    void approveGradesViewBtnAction(ActionEvent event) 
    {

    }

    @FXML
    void executeNewExamBtnAction(ActionEvent event) throws IOException 
    {
    	Stage stage = (Stage) executeNewExamBtn.getScene().getWindow();
		ExecuteNewExamGUI eneg=new ExecuteNewExamGUI();
		eneg.start(stage);
    }

	public void start(Stage primaryStage) throws IOException 
	{
		Parent root = FXMLLoader.load(getClass().getResource("examInExecutionMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
		
	}

	

}
