package gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import logic.Question;
import logic.StudentController;
import logic.StudentInExam;

public class QuestionListViewCellForStudetResults<T> 
				extends QuestionListViewCell<QuestionInExam> {

	Color c=Color.RED;
	private int answer;
	@Override
	public Boolean setCorrectAnswerLabel() {
		return true;
	}
	
	public void setCheckBoxInQuestion(String questionID) {
		StudentController st = new StudentController();
		HashMap<QuestionInExam,Integer> questions = st.getStudentResults().getCheckedAnswers();
		for (Map.Entry<QuestionInExam, Integer> entry : questions.entrySet()) {
			if(entry.getKey().getQuestion().getQuestionID().equals(questionID)) {
				answer=entry.getValue();
				if(entry.getKey().getQuestion().getCorrectAnswer()==entry.getValue())
					c=Color.GREEN;
			}
			
		}
		switch(answer) {
			case 1:
				radioButton1.setSelected(true);
				radioButton1.setBackground(new Background(new BackgroundFill(c, null, null)));
				break;
			case 2:
				radioButton2.setSelected(true);
				radioButton2.setBackground(new Background(new BackgroundFill(c, null, null)));
				break;
			case 3:
				radioButton3.setSelected(true);
				radioButton3.setBackground(new Background(new BackgroundFill(c, null, null)));
				break;
			case 4:
				radioButton4.setSelected(true);
				radioButton4.setBackground(new Background(new BackgroundFill(c, null, null)));
				break;
		}
	}

}
