package gui;

import java.util.HashMap;

public class StudentSelection {
	private static StudentSelection single_instance = null;

	public HashMap<QuestionInExam, Integer> studentAnswers;
	public QuestionInExam question;

	public StudentSelection() {
		studentAnswers = new HashMap<QuestionInExam, Integer>();
	}

	public static StudentSelection getInstance() {
		if (single_instance == null)
			single_instance = new StudentSelection();

		return single_instance;
	}

}
