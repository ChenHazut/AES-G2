package gui;

public class QuestionListViewCellForStudent<T> 
 					extends QuestionListViewCell<QuestionInExam>{

	@Override
	public Boolean setCorrectAnswerLabel() {
		return false;
	}

}