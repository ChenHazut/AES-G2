package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Course;
import logic.Exam;
import logic.Question;
import logic.TeacherController;

public class CreateExamGUI implements Initializable {

	@FXML
	private TableView<QuestionGUI> table;
	@FXML
	private TableColumn<QuestionGUI, String> questionID;
	@FXML
	private TableColumn<QuestionGUI, String> questionText;
	@FXML
	private TableColumn<QuestionGUI, String> author;
	@FXML
	private TableColumn<QuestionGUI, CheckBox> selected;
	@FXML
	private TableColumn<QuestionGUI, TextField> pointsColumn;
	@FXML
	private TextArea studentInsructions;
	@FXML
	private TextArea teacherInstructions;
	@FXML
	private TextField duration;
	@FXML
	private Button cancleButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label examIDLabel;
	@FXML
	private ComboBox<String> subjectCombo;
	@FXML
	private ComboBox<String> courseCombo;

	private ArrayList<Question> questionArr;
	private ObservableList<String> coursesL;
	ObservableList<QuestionGUI> questionsList;
	ClientConsole client;

	TeacherController tc;

	public CreateExamGUI() {
		client = new ClientConsole();
		tc = new TeacherController();

	}

	public void initData(Exam exam) {
		courseCombo.setDisable(true);
		questionsList = FXCollections.observableArrayList();
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionID"));
		questionText.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionTxt"));
		author.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("teacherName"));
		selected.setCellValueFactory(new PropertyValueFactory<QuestionGUI, CheckBox>("checkButton"));
		pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionGUI, TextField>("points"));
		
		tc.getTeacherCourse();
		for (int i = 0; i < tc.getSubjects().size(); i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		coursesL = FXCollections.observableArrayList();

		courseCombo.getItems().addAll(coursesL);
		questionArr = tc.getAllQuestions();
		setObservableListForTable(questionArr);
		if (exam != null)
			initateExamDetails(exam);

	}

	private void initateExamDetails(Exam exam) {
		duration.setText(Integer.toString(exam.getDuration()));
		teacherInstructions.setText(exam.getInstructionForTeacher());
		studentInsructions.setText(exam.getInstructionForStudent());
		// examIDLabel.setText(exam.getExamID());

		if (exam.getQuestionsArrayList() != null) {
			ArrayList<Question> questionsInExam = new ArrayList<Question>();

			for (int i = 0; i < questionArr.size(); i++) {

				Question tempQuestion = questionArr.get(i);
				for (int j = 0; j < questionsInExam.size(); j++) {
					if (tempQuestion.equals(questionsInExam.get(j)))
					questionsList.get(i).getPoints().setText(Integer.toString(exam.getQuestions().get(tempQuestion)));
					questionsList.get(i).getCheckButton().setSelected(true);
				}
			}
		}
	}

	public void cancleButtonAction() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamRepositoryGUI examRep = loader.getController();
		examRep.initData();
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}

	public void saveButtonAction() throws Exception {
		Exam exam = new Exam();
		HashMap<Question, Integer> temp = new HashMap<Question, Integer>();
		ArrayList<QuestionInExam> selectedQuestion = new ArrayList<QuestionInExam>();
		int j = 0;

		exam.setInstructionForStudent(studentInsructions.getText());
		exam.setInstructionForTeacher(teacherInstructions.getText());
		try{
			exam.setDuration(Integer.parseInt(duration.getText()));
		}catch(Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < questionsList.size(); i++) {
			if (questionsList.get(i).getCheckButton().isSelected()) {
				int pointsPerQuestion = Integer.parseInt(questionsList.get(i).getPoints().getText());
				selectedQuestion.add(new QuestionInExam(pointsPerQuestion, questionArr.get(i), ++j));
				temp.put(questionArr.get(i), pointsPerQuestion);
			}
		}
		
		exam.setQuestions(temp);
		String g =(String) courseCombo.getValue();
		Course c = tc.getCourseFromName(g);
		exam.setCourse(c);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamFormForTeacher.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamFormForTeacherGUI eFormForTeacher = loader.getController();
		eFormForTeacher.initData(exam, selectedQuestion);
		Stage stage = (Stage) table.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	public void subjectComboBoxAction(ActionEvent ae) {
		if(subjectCombo.getValue()!=null)
			courseCombo.setDisable(false);
		int i;
		courseCombo.getItems().removeAll(coursesL);
		for (i = 0; i < coursesL.size(); i++)
			coursesL.remove(i);
		for (i = 0; i < tc.getCourses().size(); i++)
			if (tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				coursesL.add(tc.getCourses().get(i).getcName());
		courseCombo.getItems().addAll(coursesL);
	}
   
	@FXML
    public void courseComboBoxAction(ActionEvent event) {
		if(courseCombo.getValue()==null || subjectCombo.getValue()==null)
			return;
		String course = courseCombo.getValue();
		ArrayList<Question> arr = new ArrayList<>();
		for (int i=0; i<questionArr.size(); i++)
		{
			ArrayList<Course> cl = questionArr.get(i).getCourseList();
			for (int j=0; j<cl.size(); j++)
				if(cl.get(j).getcName().equals(course)) {
					arr.add(questionArr.get(i));
					break;
				}
		}

		setObservableListForTable(arr);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private void setObservableListForTable(ArrayList<Question> arr) {
		questionsList.clear();
		for (int i = 0; i < arr.size(); i++) {
			CheckBox cb = new CheckBox();
			TextField tf = new TextField();
			cb.setVisible(true);
			tf.setVisible(true);
			QuestionGUI qgui = new QuestionGUI(arr.get(i).getQuestionID(), arr.get(i).getTeacherName(),
					arr.get(i).getQuestionTxt(), cb, tf);
			questionsList.add(qgui);
		}
		table.setItems(questionsList);
	}
}
