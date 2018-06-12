package gui;

import javafx.collections.ObservableList;
import org.controlsfx.control.table.TableRowExpanderColumn;

public class ExamForm {
	ObservableList<QuestionGUI> selectedQuestions;
	TableRowExpanderColumn r  ;
	
	public ExamForm(ObservableList<QuestionGUI> list) {
		
		selectedQuestions = list;
	}
}
