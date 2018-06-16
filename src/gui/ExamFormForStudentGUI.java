package gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import logic.ExamInExecution;
import logic.Question;

public class ExamFormForStudentGUI {
	
	private ObservableList<QuestionInExam> observableQuestions;
	@FXML
	private ListView<QuestionInExam> listView;
	
	public void initData(ExamInExecution exam) {
		ArrayList<Question> selectedQuestion = new ArrayList<Question>();    //to save all the array q
		observableQuestions = FXCollections.observableArrayList();
		selectedQuestion =  exam.getExamDet().getQuestionsArrayList();  //took all the questions from the execution exam
		
		int j=0;
		for (int i=0; i<selectedQuestion.size(); i++) {
			int pointsPerQuestion = exam.getExamDet().getQuestions().get(selectedQuestion.get(i));
			observableQuestions.add(new QuestionInExam(pointsPerQuestion,selectedQuestion.get(i),++j));
		}
			
		
		listView.setItems(observableQuestions);
	    listView.setCellFactory(QuestionListView -> new QuestionListViewCellForStudent<QuestionInExam>());
	    
	}

}
