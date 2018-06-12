package gui;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ExamInExecutionRow 
{
	String examID;
	String courseName;
	int executionID;
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
	
	
	
}
