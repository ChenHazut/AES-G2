package gui;

import java.io.Serializable;

import logic.Question;

public class QuestionInExam implements Serializable {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionInExam other = (QuestionInExam) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

}
