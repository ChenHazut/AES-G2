package logic;

import java.io.Serializable;

public class Question  implements Serializable
{
	private String QuestionTxt;
	private String teacherName;
	private String questionID;
	private String[] answers;
	private int correctAnswer;
	private String instruction;
	
	public Question(String questionTxt, String questionID, String teacherName, String instruction,String answer1,String answer2,String answer3,String answer4, int correctAnswer) {
		this.QuestionTxt = questionTxt;
		this.teacherName = teacherName;
		this.questionID = questionID;
		this.answers=new String[4];
		this.answers[0] = answer1;
		this.answers[1] = answer2;
		this.answers[2] = answer3;
		this.answers[3] = answer4;
		this.correctAnswer = correctAnswer;
		this.instruction=instruction;
	}
	
	
	public String getQuestionTxt() {
		return QuestionTxt;
	}
	
	public String getTeacherName() {
		return teacherName;
	}
	
	public String getQuestionID() {
		return questionID;
	}

	public String[] getAnswers() {
		return answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String getInstruction() {
		return instruction;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
		
	}
	public String toString()
	{
		return questionID + QuestionTxt;
	}



	
	
}
