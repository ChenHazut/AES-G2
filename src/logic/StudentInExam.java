package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

public class StudentInExam implements Serializable{

	private int grade;
	private String examID;
	private Timestamp date; //the date of the exam
	private String courseName; //the name of the course
	private String studentID;
	private HashMap<Question, Integer> answersInExam;
	
	public String getStudentID() {
		return studentID;
	}




	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}




	public StudentInExam(String examID ,int grade ,Timestamp date,String course_name, String studentID )
	{
		this.grade=grade;
		this.examID=examID;
		this.date=date;
		this.courseName= course_name;
		this.studentID=studentID;
	}
	



	public String getCourseName() {
		return courseName;
	}




	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}




	public Timestamp getDate() {
		return date;
	}


	public void setDate(Timestamp date) {
		this.date = date;
	}
	

	public int getGrade() {
		return grade;
	}



	public void setGrade(int grade) {
		this.grade = grade;
	}



	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}
	
	
	
	
}
