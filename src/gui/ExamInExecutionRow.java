package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ExamInExecutionRow 
{
	String examID;
	String courseName;
	String courseID;
	String subjectID;
	int executionID;
	String executeTeacherName;
	String authorTeacherName;
	int duration;
	private ImageView preview;
	
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
	
	
	
	
}
