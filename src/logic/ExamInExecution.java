package logic;

import java.io.Serializable;

public class ExamInExecution implements Serializable
{
	private Exam examDet;
	private int ExecutionID;
	private boolean isLocked;
	private User execTeacher;
	private String courseName;
	
	public ExamInExecution()
	{
		examDet=new Exam();
		execTeacher=new User();
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
	public User getExecTeacher() {
		return execTeacher;
	}
	public void setExecTeacher(User execTeacher) {
		this.execTeacher = execTeacher;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
}
