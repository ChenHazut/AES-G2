package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import gui.QuestionInExam;

public class StudentInExam implements Serializable {

	private String studentID;
	private String studentName;
	private int grade;
	private String examID;
	private int executionID;
	private Timestamp date; // the date of the exam
	private String courseName; // the name of the course
	private String studentStatus;
	private HashMap<QuestionInExam, Integer> checkedAnswers;
	private int NumberOfCorrectAnswer;
	private int NumberOfWrongAnswer;
	private Boolean isComp;
	private int actualDuration;

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

	public HashMap<QuestionInExam, Integer> getCheckedAnswers() {
		return checkedAnswers;
	}

	public void setCheckedAnswers(HashMap<QuestionInExam, Integer> checkedAnswers) {
		this.checkedAnswers = checkedAnswers;
	}

	public int getNumberOfWrongAnswer() {
		return NumberOfWrongAnswer;
	}

	public void setNumberOfWrongAnswer(int numberOfWrongAnswer) {
		NumberOfWrongAnswer = numberOfWrongAnswer;
	}

	public int getNumberOfCorrectAnswer() {
		return NumberOfCorrectAnswer;
	}

	public void setNumberOfCorrectAnswer(int numberOfCorrectAnswer) {
		NumberOfCorrectAnswer = numberOfCorrectAnswer;
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

	public String toString() {
		String s = "";
		s += "\nExam ID: " + getExamID() + "\tExam Date" + getDate() + "\tCourse name: " + getCourseName() + "\n";
		if (studentStatus.equals("finished")) {
			s += "Student Grade: " + getGrade() + "\n" + "Correct Answers: " + getNumberOfCorrectAnswer()
					+ "\tWrong Answers: " + getNumberOfWrongAnswer();
			for (Map.Entry<QuestionInExam, Integer> entry : checkedAnswers.entrySet()) {
				s += entry.getKey().toString() + "\nAnswer selected: " + entry.getValue();
			}
		} else {
			s += "The student hasn't finished the exam\n";
		}
		return s;
	}

}