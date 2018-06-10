package gui;

import logic.Exam;
import logic.Question;


public  class GUImanager
{
	 public static Question selectedQuestion;
	 public static Question updatedQuestion;
	public  static Exam selectedExam;

	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

	public Question getUpdatedQuestion() {
		// TODO Auto-generated method stub
		return updatedQuestion;
	}
	
	public void setUpdatedQuestion(Question uq) {
		// TODO Auto-generated method stub
		this.updatedQuestion=uq;
	}
}
