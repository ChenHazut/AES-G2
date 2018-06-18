package gui;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.StudentController;

public class ComputerizedExamGUI implements Initializable {
 
	@FXML
	Label examID;
	@FXML
	Label courseName;
	@FXML
	Button okButton;
	@FXML
	TextField insertExamCode;
	@FXML
	private Label errorL;

	ExamInExecution exam;
	
	String examCode;
	
	StudentController st;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

	}

	public void initData(ExamInExecution examInExecution)
	{
		exam=examInExecution;
		examID.setText(exam.getExamDet().getExamID());
		examID.setDisable(true);
		courseName.setText(exam.getCourseName());
		courseName.setDisable(true);
	}
	
	public void okButtonAction() throws IOException
	{
		examCode = insertExamCode.getText();
		
		if(examCode.equals("") ) //check if the student entered ok before enter the exam data
		{
			///if the student enter 'ok' and didn't enter data
			errorL.setText("You have to enter the exam code first");
		}
		
		else
		{
			///if the student enter the write exam code
			if(examCode.equals(exam.getExamCode()))
			{
				///open the exam in the computer
				st = new StudentController();
				ExamInExecution examToPerform=st.performCompExam(exam); //to get the questions details of the exam
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("ExamFormForStudent.fxml"));
				
				Parent root = loader.load();
				Scene scene = new Scene(root);
				ExamFormForStudentGUI ExamForStudent = loader.getController();		
				ExamForStudent.initData(examToPerform,true);
				Stage stage = (Stage) okButton.getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			}
			//if the student didnt write the good code
			else
			{
				errorL.setText("The exam code is incorrect!");
			}
		}
			
				
	}

   
	
}
