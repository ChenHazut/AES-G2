package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.StudentInExam;
import logic.TeacherController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
    
    @FXML
    private ImageView lockedImg;
    
    @FXML
    private ImageView lockBtnImg;

    @FXML
    private Label lockLable;

    @FXML
    private ImageView overTimeBtnImg;

    @FXML
    private Label overtimeLable;
    
    @FXML
    private Label examStatLabel;

    @FXML
    private ImageView statImg;

    @FXML
    private Button statBtn;

    @FXML 
    private PieChart pieChart;
    
    ExamInExecutionRow exam;
    
    TeacherController tc;
    
    ObservableList<StudentInExam> studentOL;
    
    ObservableList<PieChart.Data> pieChartData;
    
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
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("");
    	alert.setHeaderText("Are you sure you want to lock the exam?");
    	alert.setContentText("In case the Exam is locked all student that didn't submit Won't be able to submit. "
    			+ "To continue press OK");
    	alert.setWidth(500);
    	alert.setHeight(320);
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		System.out.println("ok is pressed ");
    		tc.lockExam(exam.getExam());
    	} 
    }

    @FXML
    void requestOvertimeBtnAction(ActionEvent event) 
    {

    }
    

	public void initData(ExamInExecutionRow selectedItem, String status) 
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
    	
		if(status.equals("locked"))
		{
			lockedImg.setVisible(true);
			lockBtnImg.setVisible(false);
			lockExamBtn.setVisible(false);
			lockLable.setVisible(false);
			overTimeBtnImg.setVisible(false);
			overtimeLable.setVisible(false);
			requestOvertimeBtn.setVisible(false);
		
		}
		else
		{
			int startedCount=0;
			int notStartedCount=0;
			int finishedCount=0;
			statBtn.setVisible(false);
			statImg.setVisible(false);
			examStatLabel.setVisible(false);
			for(StudentInExam s: sl)
			{
				if(s.getStudentStatus().equalsIgnoreCase("finished"))
					finishedCount++;
				else if(s.getStudentStatus().equalsIgnoreCase("started"))
					startedCount++;
				else if(s.getStudentStatus().equalsIgnoreCase("notstarted"))
					notStartedCount++;
			}
			
			ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	                new PieChart.Data("Finished", finishedCount),
	                new PieChart.Data("Started", startedCount),
	                new PieChart.Data("NotStarted", notStartedCount));
			pieChart.setData(pieChartData);
			
		}
		
		
	}
	
	@FXML
    void statBtnAction(ActionEvent event) 
	{
		
    }

	

}
