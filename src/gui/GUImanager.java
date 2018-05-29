package gui;

import logic.Question;

public  class GUImanager
{
	 public static Question selectedQuestion;

	public Question getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}
}
