package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class ExamInExecution implements Serializable
{
	private Exam examDet;   // the exam
	private int ExecutionID; 
	private boolean isLocked;
	private String execTeacher;
	private String courseName;
	private String courseID;
	private String subjectID;
	private String examCode;
	private ArrayList<StudentInExam> students;
	private boolean isGroup;  //status to know if the student in in group-1 ro not-0
	
	public ExamInExecution() 
	{
		
	}
	
	
	public Exam getExamDet() {
		return examDet;
	}
	public void setExamDet(Exam examDet) {
		this.examDet = examDet;
	}
	public int getExecutionID() {
		return ExecutionID;
	}
	public void setExecutionID(int executionID) {
		ExecutionID = executionID;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getExecTeacher() {
		return execTeacher;
	}
	public void setExecTeacher(String execTeacher) {
		this.execTeacher = execTeacher;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ArrayList<StudentInExam> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<StudentInExam> students) {
		this.students = students;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	
	
	
}