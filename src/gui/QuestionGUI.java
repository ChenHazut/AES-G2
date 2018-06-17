package gui;

import java.io.Serializable;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class QuestionGUI implements Serializable {
	private String questionID;
	private String teacherName;
	private String questionTxt;
	private ImageView image;
	private CheckBox checkButton;
	private TextField points;

	public QuestionGUI(String questionID, String teacherName, String questionTxt, ImageView image) {
		super();
		this.questionID = questionID;
		this.teacherName = teacherName;
		this.questionTxt = questionTxt;
		this.image = image;
	}

	public QuestionGUI(String questionID, String teacherName, String questionTxt, CheckBox cb, TextField tf) {
		super();
		this.questionID = questionID;
		this.teacherName = teacherName;
		this.questionTxt = questionTxt;
		this.checkButton = cb;
		points = tf;
	}

	public void setImage(ImageView value) {
		image = value;
	}

	public ImageView getImage() {
		return image;
	}

	public String getQuestionID() {
		return questionID;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getQuestionTxt() {
		return questionTxt;
	}

	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public CheckBox getCheckButton() {
		return checkButton;
	}

	public TextField getPoints() {
		return points;
	}

	public void setPoints(TextField points) {
		this.points = points;
	}
}