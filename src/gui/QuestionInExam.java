package gui;

import logic.Question;

public class QuestionInExam {

	private int pointsInExam;
	private Question question;
	private int serialNumberInExam;
	
	public QuestionInExam(int pointsInExam, Question question, int serialNumberInExam) {
		this.setPointsInExam(pointsInExam);
		this.setQuestion(question);
		this.setSerialNumberInExam(serialNumberInExam);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public int getPointsInExam() {
		return pointsInExam;
	}

	public void setPointsInExam(int pointsInExam) {
		this.pointsInExam = pointsInExam;
	}

	public int getSerialNumberInExam() {
		return serialNumberInExam;
	}

	public void setSerialNumberInExam(int serialNumberInExam) {
		this.serialNumberInExam = serialNumberInExam;
	}
	
	
	
	
}
