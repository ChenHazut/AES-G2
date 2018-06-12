package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import logic.ClientConsole;
import logic.Course;
import logic.Exam;
import logic.TeacherController;

public class ExecuteNewExamGUI implements Initializable {

    @FXML
    private TableView<Exam> table;

    @FXML
    private TableColumn<Exam, Label> previewCol;

    @FXML
    private TableColumn<Exam, String> examIDCol;

    @FXML
    private TableColumn<Exam, String> teacherNameCol;

    @FXML
    private TableColumn<Exam, String> cNameCol;

    @FXML
    private TableColumn<Exam, Integer> durationCol;

    @FXML
    private ComboBox<String> subjectCombo;

    @FXML
    private ComboBox<String> courseCombo;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchByExamTF;
    
    TeacherController tc;
    
    ObservableList<String> coursesL;
    
    ClientConsole client;
    
    public ExecuteNewExamGUI() {
		super();
		tc=new TeacherController();
		client=new ClientConsole();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	tc.getTeacherCourse();
		for(int i=0;i<tc.getSubjects().size();i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		coursesL=FXCollections.observableArrayList();
		
	}
    
    @FXML
    void nextBtnAction(ActionEvent event)
    {
    	
    }
    
    @FXML
    void searchButtonAction(ActionEvent event) 
    {
    	Message msg=new Message();
    	msg.setClassType("Teacher");
    	if(searchByExamTF.getText()!=null)
    	{
    		msg.setSentObj(searchByExamTF.getText());
    		msg.setqueryToDo("getExamByExamID");
    	}
    	else if(subjectCombo.getValue()!=null && courseCombo.getValue()!=null)
    	{
    		Course c=new Course(courseCombo.getValue(),subjectCombo.getValue());
    		msg.setSentObj(c);
    		msg.setqueryToDo("getExamsBycourse");
    	}
    	else return;
    	client.accept(msg);
    	try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	msg=client.getMessage();
    	ArrayList<Exam> exams=new ArrayList<Exam>();
    	
    }
    
    @FXML
    void choseSubject(ActionEvent event) 
    {
		int i;
		for(i=0;i<coursesL.size();i++)
			coursesL.remove(i);
		for(i=0;i<tc.getCourses().size();i++)
			if(tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				coursesL.add(tc.getCourses().get(i).getcName());
		courseCombo.getItems().addAll(coursesL);
    }
	

}
