package logic;

import java.io.Serializable;
import java.sql.Timestamp;

public class StudentInExam implements Serializable{
	private String studentID;
	private String studentName;
	private int grade;
	private String examID;
	private Timestamp date; //the date of the exam
	private String course_name; //the name of the course
	
	
	
	public StudentInExam() {
		super();
	}

	public StudentInExam(String examID ,int grade ,Timestamp date,String course_name )
	{
		this.grade=grade;
		this.examID=examID;
		this.date=date;
		this.course_name= course_name;
	}
	
	public String getCourse_name() {
		return course_name;
	}


	public void setCourse_name(String course_name) {
		this.course_name = course_name;
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
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	
	
	/*public void Set_actualdoration(int actualdoration)
	{
		this.actualdoration=actualdoration;
	}
	
	public int Get_actualdoration()
	{
		return this.actualdoration;
	}*/
	

	
	/*public void Set_studentID(String studentID)
	{
		this.studentID=studentID;
	}
	
	public String Get_studentID()
	{
		return this.studentID;
	}*/
	
	/*public void Set_date(String date)
	{
		this.date=date;
	}
	
	public String Get_date()
	{
		return this.date;
	}*/
	
	
}