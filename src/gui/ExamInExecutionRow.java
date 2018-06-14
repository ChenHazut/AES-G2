package gui;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import logic.Exam;
import logic.ExamInExecution;

public class ExamInExecutionRow 
{
	ExamInExecution exam;
	String examID;
	String courseName;
	String courseID;
	String subjectID;
	int executionID;
	String executeTeacherName;
	String authorTeacherName;
	int duration;
	RadioButton selectExamRB;
	private ImageView preview;
	
	
	
	public ExamInExecutionRow(ExamInExecution exam,ImageView iv) {
		super();
		this.exam=exam;
		this.examID = exam.getExamDet().getExamID();
		this.courseName=exam.getCourseName();
	//	this.courseID=exam.getCourseID();
	//	this.subjectID=exam.getSubjectID();
		this.executionID=exam.getExecutionID();
//		this.executeTeacherName=exam.getExecTeacher().getuName();
//		this.authorTeacherName=exam.getExamDet().getTeacherName();
//		this.duration=exam.getExamDet().getDuration();
//		this.selectExamRB=rb;
		this.preview=iv;
		
	}
	
	public ExamInExecutionRow() {

		
	}
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public int getExecutionID() {
		return executionID;
	}
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}
	public ImageView getPreview() {
		return preview;
	}
	public void setPreview(ImageView previewL) {
		this.preview = previewL;
	}
	public String getExecuteTeacherName() {
		return executeTeacherName;
	}
	public void setExecuteTeacherName(String executeTeacherName) {
		this.executeTeacherName = executeTeacherName;
	}
	public String getAuthorTeacherName() {
		return authorTeacherName;
	}
	public void setAuthorTeacherName(String authorTeacherName) {
		this.authorTeacherName = authorTeacherName;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setSubjectID(String subjectID) {
		this.subjectID=subjectID;
	}
	public String getSubjectID() {
		return subjectID;
	}
	public RadioButton getSelectExamRB() {
		return selectExamRB;
	}
	public void setSelectExamRB(RadioButton selectExamRB) {
		this.selectExamRB = selectExamRB;
	}

	public ExamInExecution getExam() {
		return exam;
	}
	
	
	
	
}
