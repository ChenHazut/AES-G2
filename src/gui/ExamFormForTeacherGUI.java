package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.Exam;
import logic.TeacherController;

public class ExamFormForTeacherGUI implements Initializable {
	
	private ObservableList<QuestionInExam> observableQuestions;

    @FXML
    private TextArea insrtuctionsForStudent;
    @FXML
    private TextArea insrtuctionsForTeacher;
    @FXML
    private TextField duration;
	@FXML
	private Label examIDLabel;
    @FXML
    private Label teacherNameLabel;
    @FXML
    private TextField subjectName;
    @FXML
    private TextField courseName;
	@FXML
	private ListView<QuestionInExam> listView;
	@FXML
	private Button cancleButton;
	@FXML
	private Button saveButton;
	private Exam exam;
	
	public void initData(Exam exam, ArrayList<QuestionInExam> selectedQuestions) {
		this.exam = exam;
		insrtuctionsForTeacher.setText(exam.getInstructionForTeacher());
		insrtuctionsForStudent.setText(exam.getInstructionForStudent());
		duration.setText(Integer.toString(exam.getDuration()));
		examIDLabel.setText(exam.getExamID());
		teacherNameLabel.setText(exam.getTeacherName());
		
		observableQuestions = FXCollections.observableArrayList(selectedQuestions);
		listView.setItems(observableQuestions);
	    listView.setCellFactory(QuestionListView -> new QuestionListViewCellForTeacher<QuestionInExam>());
	    
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	public void saveButtonAction() {
		TeacherController tc = new TeacherController();
		exam.setTeacherID("11111");
		exam.setWasUsed(false);
		
		tc.saveExam(exam);
	}
	
	public void cancleButtonAction() {
		
	}
}
