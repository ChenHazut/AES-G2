package logic;

import java.io.Serializable;
import java.util.HashMap;

public class Exam implements Serializable
{
	private HashMap<Question, Integer> questions;
	private String teacherID;
	private String teacherName;
	private Boolean wasUsed;
	private String ExamID;
	private String instructionForTeacher;
	private String instructionForStudent;
	private int duration;
	private Course course;
	private String courseName;
	
	public Exam() {
		
	}
	public Exam(String teacherID, String teacherName, Boolean wasUsed, String examID, String instructionForTeacher,
			String instructionForStudent, int duration, Course course) {
		super();
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.wasUsed = wasUsed;
		ExamID = examID;
		this.instructionForTeacher = instructionForTeacher;
		this.instructionForStudent = instructionForStudent;
		this.duration = duration;
		this.course = course;
	}
	public HashMap<Question, Integer> getQuestions() {
		return questions;
	}
	public void setQuestions(HashMap<Question, Integer> questions) {
		this.questions = questions;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Boolean getWasUsed() {
		return wasUsed;
	}
	public void setWasUsed(Boolean wasUsed) {
		this.wasUsed = wasUsed;
	}
	public String getExamID() {
		return ExamID;
	}
	public void setExamID(String examID) {
		ExamID = examID;
	}
	public String getInstructionForTeacher() {
		return instructionForTeacher;
	}
	public void setInstructionForTeacher(String instructionForTeacher) {
		this.instructionForTeacher = instructionForTeacher;
	}
	public String getInstructionForStudent() {
		return instructionForStudent;
	}
	public void setInstructionForStudent(String instructionForStudent) {
		this.instructionForStudent = instructionForStudent;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
		courseName=course.getcName();
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	
}
