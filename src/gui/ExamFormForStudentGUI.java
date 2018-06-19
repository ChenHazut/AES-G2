package gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import logic.ExamInExecution;
import logic.Question;

public class ExamFormForStudentGUI {
	
	private ObservableList<QuestionInExam> observableQuestions;
	@FXML
	private ListView<QuestionInExam> listView;

    @FXML
    private Text courseName;

    @FXML
    private Text instructions;

    @FXML
    private Text duration;

    @FXML
    private Text exeTeacherName;
    @FXML
    private Button submit;

    @FXML
    private ImageView disableListView;
    private Boolean studentSolveExam;
	
	public void initData(ExamInExecution exam, Boolean studentSolveExam) {
		this.studentSolveExam = studentSolveExam;
		exeTeacherName.setText(exam.getExecTeacher().getuName());
		duration.setText(Integer.toString(exam.getExamDet().getDuration()));//לשנות לזמן בפועל
		instructions.setText(exam.getExamDet().getInstructionForStudent());
		courseName.setText(exam.getExamDet().getCourse().getcName());
		ArrayList<Question> selectedQuestion = new ArrayList<Question>();    //to save all the array q
		observableQuestions = FXCollections.observableArrayList();
		if(exam.getExamDet().getQuestions()!=null)
			selectedQuestion =  exam.getExamDet().getQuestionsArrayList();  //took all the questions from the execution exam
		else selectedQuestion=exam.getExamDet().getQuestionArr();
		int j=0;
		for (int i=0; i<selectedQuestion.size(); i++) {
			int pointsPerQuestion = exam.getExamDet().getQuestions().get(selectedQuestion.get(i));
			observableQuestions.add(new QuestionInExam(pointsPerQuestion,selectedQuestion.get(i),++j));
		}
			
		if(!studentSolveExam) {
			listView.setEditable(false);
			submit.setDisable(true);
		}
		listView.setItems(observableQuestions);
	    listView.setCellFactory(QuestionListView -> new QuestionListViewCellForStudent<QuestionInExam>());
	    
	}

}
