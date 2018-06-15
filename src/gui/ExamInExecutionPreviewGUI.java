package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.StudentInExam;
import logic.TeacherController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.MaskerPane;

public class ExamInExecutionPreviewGUI implements Initializable{

    @FXML
    private Button backBtn;

    @FXML
    private Button lockExamBtn;

    @FXML
    private MaskerPane masker;
    
    @FXML
    private TableView<StudentInExam> table;

    @FXML
    private TableColumn<StudentInExam, String> sIDCol;

    @FXML
    private TableColumn<StudentInExam, String> sNameCol;

    @FXML
    private TableColumn<StudentInExam, String> isSubmittedCol;

    @FXML
    private Button requestOvertimeBtn;

    @FXML
    private Label examIDLabel;

    @FXML
    private Label courseNameLabel;

    @FXML
    private Label isGroupLabel;
    
    ExamInExecutionRow exam;
    
    TeacherController tc;
    
    ObservableList<StudentInExam> studentOL;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	
	}

    @FXML
    void backBtnAction(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("examInExecutionMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ExamInExecutionMenuGUI examInExecMenu=loader.getController();
        examInExecMenu.initData();
        Stage window = (Stage)backBtn.getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void lockExamBtnAction(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("errorBox.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ErrorBoxGUI errorBox=loader.getController();
        errorBox.initData("Are you sure you want to lock the exam?\nin case the Exam is locked all student that didn't submit Won't be able to submit.\nTo continue press OK");
        Boolean result= errorBox.getResult();
//        if(result)
//        	tc.lockExam();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void requestOvertimeBtnAction(ActionEvent event) 
    {

    }
    

	public void initData(ExamInExecutionRow selectedItem) 
	{
		studentOL = FXCollections.observableArrayList();
		tc=new TeacherController();
		this.exam=selectedItem;
		examIDLabel.setText(exam.getExamID());
		courseNameLabel.setText(exam.getCourseName());
		isGroupLabel.setText(exam.getExam().isGroup()?"Group":"All");
		sIDCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
    	sNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
    	isSubmittedCol.setCellValueFactory(new PropertyValueFactory<>("studentStatus"));
    	ExamInExecution e=new ExamInExecution();
    	e.getExamDet().setExamID(exam.getExamID());
    	e.setExecutionID(exam.getExecutionID());
    	ArrayList<StudentInExam> sl=tc.getExamnieesOfExam(e);
    	
    	if(sl!=null)
    	{
    		studentOL.addAll(sl);
    		table.setItems(studentOL);
    	}
    	
    	
		
	}

	

}
