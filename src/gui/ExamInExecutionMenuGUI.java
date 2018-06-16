package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
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
    private TableView<ExamInExecutionRow> examsTableLocked;
    
    @FXML
    private TableColumn<ExamInExecutionRow, ImageView> imageColLocked;
    
    @FXML
    private TableColumn<ExamInExecutionRow, String> examIDColLocked;

    @FXML
    private TableColumn<ExamInExecutionRow, String> executionIDColLocked;

    @FXML
    private TableColumn<ExamInExecutionRow, String> courseNameColLocked;

    @FXML
    private Button executeNewExamBtn;

    @FXML
    private Button approveGradesViewBtn;

    private TeacherController tc;
    
    private ObservableList<ExamInExecutionRow> examArr;
    private ObservableList<ExamInExecutionRow> LockedExamArr; 
    private ArrayList<ExamInExecution>arr;
    private ArrayList<ExamInExecution>arrLocked;
    
    public ExamInExecutionMenuGUI()
    {
    	tc=new TeacherController();
    	examArr= FXCollections.observableArrayList();
    	LockedExamArr= FXCollections.observableArrayList();
    	arr=tc.getAllExamsInExecutionForTeacher();
    	arrLocked=tc.getLockedExamsForTeacher();
    	
    	
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) 
    {
			
	}
    
    @FXML
    public void chooseExamInExecutionL(MouseEvent me) throws IOException
    {
    	if(me.getClickCount()==2&&examsTableLocked.getSelectionModel().getSelectedItem()!=null)
    	{
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("examInExecutionPreview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ExamInExecutionPreviewGUI examInExecMenu=loader.getController();
            examInExecMenu.initData(examsTableLocked.getSelectionModel().getSelectedItem(),"locked");
            Stage window = (Stage)examsTableLocked.getScene().getWindow();
            window.setScene(scene);
            window.show();
    	}
    }
    
    @FXML
    public void chooseExamInExecution(MouseEvent me) throws IOException
    {
    	if(me.getClickCount()==2&&examsTable.getSelectionModel().getSelectedItem()!=null)
    	{
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("examInExecutionPreview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            ExamInExecutionPreviewGUI examInExecMenu=loader.getController();
            examInExecMenu.initData(examsTable.getSelectionModel().getSelectedItem(),"open");
            Stage window = (Stage)examsTable.getScene().getWindow();
            window.setScene(scene);
            window.show();
    	}
    }


	@FXML
    void approveGradesViewBtnAction(ActionEvent event) 
    {

    }

    @FXML
    void executeNewExamBtnAction(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ExecuteNewExam.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ExecuteNewExamGUI executeNewExam=loader.getController();
        executeNewExam.initData();
        Stage window = (Stage)examsTable.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


	public void initData() {
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDCol.setCellValueFactory(new PropertyValueFactory<>("executionID") );
		courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageCol.setCellValueFactory(new PropertyValueFactory<>("preview"));
		int i;
		for(i=0;i<arr.size();i++)
    	{
    		ImageView im=new ImageView(new Image("/images/preview.png"));
    		im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
    		ExamInExecutionRow e=new ExamInExecutionRow(arr.get(i),im);
    		examArr.add(e);
    	}
		examsTable.setItems(examArr);
		examIDColLocked.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDColLocked.setCellValueFactory(new PropertyValueFactory<>("executionID") );
		courseNameColLocked.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageColLocked.setCellValueFactory(new PropertyValueFactory<>("preview"));

		for(i=0;i<arrLocked.size();i++)
    	{
    		ImageView im=new ImageView(new Image("/images/padlock.png"));
    		im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
    		ExamInExecutionRow e=new ExamInExecutionRow(arrLocked.get(i),im);
    		LockedExamArr.add(e);
    	}
		examsTableLocked.setItems(LockedExamArr);	
		
	}

	

}
