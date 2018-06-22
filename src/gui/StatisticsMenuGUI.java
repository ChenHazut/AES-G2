package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Course;
import logic.Exam;
import logic.ExamInExecution;
import logic.ExamReport;
import logic.LoginController;
import logic.PrincipalController;
import logic.StudentInExam;
import logic.TeacherController;
import logic.User;


public class StatisticsMenuGUI implements Initializable
{
	    @FXML
	    private ComboBox<String> reportCombo;

	    @FXML
	    private ComboBox<User> personCombo;

	    @FXML
	    private ComboBox<ExamReport> examCombo;
	    
	    @FXML
	    private ComboBox<Course> courseCombo;
		@FXML
		BarChart<Integer,String>  histograma;
		@FXML
		GridPane grid;
	    
	    private ArrayList<User> teacherL;
	    private ArrayList<User> studentL;
	    private ArrayList<Course> courseL;
	    private PrincipalController pc;
	    
	    @FXML
	    void examComboAction(ActionEvent event) {
	    	ExamReport reportToDisplay = examCombo.getValue();
	    	initiateReport(reportToDisplay);
	    }

	    @FXML
	    void personComboAction(ActionEvent event) {
	    	User temp = personCombo.getValue();
	    	ArrayList<ExamReport> arr;
	    	if(reportCombo.getValue().equals("Teacher")) {
	    		arr = pc.getAllExamReportsTeacherWrote(temp);
		    	examCombo.getItems().addAll(arr);
	    	}	
	    	else {
	    		examCombo.setVisible(false);
	    		ArrayList<StudentInExam> studentGrades =
	    				pc.getAllExamReportsStudentPerformed(temp);
	    		ArrayList<Integer> gradeArr = new ArrayList<Integer>();
	    		for(int i=0; i<studentGrades.size(); i++) {
	    			gradeArr.add(studentGrades.get(i).getGrade());
	    		}
	    		
	    		ExamReport er = new ExamReport(gradeArr,null,0);
	    		
	    		initiateReport(er);
	    	}

	    }
	    
	    @FXML
	    void courseComboAction(ActionEvent event) {
	    	Course temp = courseCombo.getValue();
	    	ArrayList<ExamReport> arr = pc.getAllExamsInCourse(temp);
	    	examCombo.getItems().addAll(arr);
	    }

	    @FXML
	    void reportComboAction(ActionEvent event) {
	    	String temp = reportCombo.getValue();
	    	
	    	if(temp.equals("Student")) {
	    		personCombo.getItems().clear();
	    		personCombo.getItems().addAll(studentL);
	    		personCombo.setVisible(true);
	    		personCombo.setPromptText("Student List");
	    		courseCombo.setVisible(false);
	    	}
	    	else if (temp.equals("Teacher")){
	    		personCombo.getItems().clear();
	    		personCombo.getItems().addAll(teacherL);
	    		personCombo.setVisible(true);
	    		personCombo.setPromptText("Teacher List");
	    		courseCombo.setVisible(false);
	    		examCombo.setVisible(true);
	    	}
	    	else {
	    		courseCombo.getItems().addAll(courseL);
	    		personCombo.setVisible(false);
	    		courseCombo.setVisible(true);
	    		courseCombo.setPromptText("CourseList");
	    		examCombo.setVisible(true);
	    	}
	    	
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			
		}
	
		public void initData(User user) {
			if(user.getTitle().equals("Teacher")) {
				TeacherController tc=new TeacherController();
				personCombo.setDisable(true);
				courseCombo.setDisable(true);
				reportCombo.setDisable(true);
				ArrayList<ExamReport> arr = tc.getAllExamReportsTeacherWrote(user);
		    	examCombo.getItems().addAll(arr);
			}
			else {
				pc= new PrincipalController();
				reportCombo.getItems().addAll("Student","Teacher","Course");
				studentL=pc.getAllStudentsInData();
				teacherL=pc.getAllTeachersInData();
				courseL=pc.getAllCoursesInData();
			}
			
		}
		private void initiateReport(ExamReport report) {
			
			CategoryAxis xAxis = new CategoryAxis();   
	        
			xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(report.headers))); 
			xAxis.setLabel("per");  

			//Defining the y axis 
			NumberAxis yAxis = new NumberAxis(); 
			yAxis.setLabel("score");
			BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);  
			barChart.setTitle("Grade Histogram");
			
			XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
			for(int i=0; i<report.getPercentages().length; i++) {
				series1.getData().add(new XYChart.Data<>(report.headers[i],report.getPercentages()[i]));
			}
				barChart.getData().add(series1);
			
			
			
			
				grid.add(barChart, 0, 0);
			
			
			
			
			/*final CategoryAxis xAxis = new CategoryAxis(); // X axis is with the grades categories.
		    final NumberAxis yAxis = new NumberAxis(); // Y axis is with the numbers of grades for each category.

			XYChart.Series series1 = new XYChart.Series();
			for(int i=0; i<report.getPercentages().length; i++) {
				series1.getData().add(new XYChart.Data(report.getPercentages()[i], 
						report.headers[i]));
				
			}
	        
			histograma.getData().add(series1);
		    histograma.setTitle("Grades histogram");
		    xAxis.setLabel("Grades");       
		    yAxis.setLabel("Number of exams");*/
		}
}


