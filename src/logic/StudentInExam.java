package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

public class StudentInExam implements Serializable {
	private String studentID;
	private String studentName;
	private int grade;
	private String examID;
	private int executionID;
	private Timestamp date; // the date of the exam
	private String courseName; // the name of the course
	private String studentStatus;
	private int numberOfCorrectAnswers;
	private int numberOfWrongAnswers;
	private int actualDuration;
	private Boolean isComp;
	private HashMap<Question, Integer> checkedAnswers;

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

	public HashMap<Question, Integer> getCheckedAnswers() {
		return checkedAnswers;
	}

	public void setCheckedAnswers(HashMap<Question, Integer> checkedAnswers) {
		this.checkedAnswers = checkedAnswers;
	}

	public int getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}

	public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
		this.numberOfCorrectAnswers = numberOfCorrectAnswers;
	}

	public int getNumberOfWrongAnswers() {
		return numberOfWrongAnswers;
	}

	public void setNumberOfWrongAnswers(int numberOfWrongAnswers) {
		this.numberOfWrongAnswers = numberOfWrongAnswers;
	}

	public int getActualDuration() {
		return actualDuration;
	}

	public void setActualDuration(int actualDuration) {
		this.actualDuration = actualDuration;
	}

	public Boolean getIsComp() {
		return isComp;
	}

	public void setIsComp(Boolean isComp) {
		this.isComp = isComp;
	}

	public boolean equals(Object arg0) {
		StudentInExam s = (StudentInExam) arg0;
		if (!s.getStudentID().equals(this.studentID))
			return false;
		if (!s.getExamID().equals(this.examID))
			return false;
		if (s.getExecutionID() != this.executionID)
			return false;
		return true;
	}

}