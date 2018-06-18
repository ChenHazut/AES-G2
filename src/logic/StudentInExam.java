package logic;

import java.io.Serializable;
import java.sql.Timestamp;

public class StudentInExam implements Serializable {
	private String studentID;
	private String studentName;
	private int grade;
	private String examID;
	private int executionID;
	private Timestamp date; // the date of the exam
	private String courseName; // the name of the course
	private String studentStatus;

	public StudentInExam() {
		super();
	}

	public StudentInExam(String examID, int grade, Timestamp date, String course_name, String studentID) {
		this.grade = grade;
		this.examID = examID;
		this.date = date;
		this.courseName = course_name;
		this.studentID = studentID;
	}

	public StudentInExam(String examID, int grade, Timestamp date, String course_name) {
		this.grade = grade;
		this.examID = examID;
		this.date = date;
		this.courseName = course_name;
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

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public int getExecutionID() {
		return executionID;
	}

	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	/*
	 * public void Set_actualdoration(int actualdoration) {
	 * this.actualdoration=actualdoration; }
	 * 
	 * public int Get_actualdoration() { return this.actualdoration; }
	 */

	/*
	 * public void Set_studentID(String studentID) { this.studentID=studentID; }
	 * 
	 * public String Get_studentID() { return this.studentID; }
	 */

	/*
	 * public void Set_date(String date) { this.date=date; }
	 * 
	 * public String Get_date() { return this.date; }
	 */

}