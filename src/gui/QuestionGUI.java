package gui;

import java.io.Serializable;

import javafx.scene.image.ImageView;

public class QuestionGUI implements Serializable{
	private String questionID;
	private String teacherName;
	private String questionTxt;
    private ImageView image;

  

    public QuestionGUI(String questionID, String teacherName, String questionTxt, ImageView image) {
		super();
		this.questionID = questionID;
		this.teacherName = teacherName;
		this.questionTxt = questionTxt;
		this.image = image;
	}

	public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }

	public String getQuestionID() {
		// TODO Auto-generated method stub
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
	
	
}