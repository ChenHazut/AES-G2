package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Exam 
{
	HashMap<Question, Integer> questions;
	String teacherID;
	String teacherName;
	Boolean wasUsed;
	String ExamID;
	String instructionForTeacher;
	String instructionForStudent;
	int duration;
	Course course;
	
	
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
	}
	
	
	
}
