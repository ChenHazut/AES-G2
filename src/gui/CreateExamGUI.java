package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ComboBox<String> courseComboBox;
	@FXML
	private ComboBox<String> subjectComboBox;
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

	private ArrayList<Question> questionArr;
	private ObservableList<String> coursesList;
	ObservableList<QuestionGUI> questionsList;
	ClientConsole client;

	TeacherController tc;

	public CreateExamGUI() {
		client = new ClientConsole();
		tc = new TeacherController();

	}

	public void initData(Exam exam) {
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionID"));
		questionText.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionTxt"));
		author.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("teacherName"));
		selected.setCellValueFactory(new PropertyValueFactory<QuestionGUI, CheckBox>("checkButton"));
		pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionGUI, TextField>("points"));

		setObservableListForTable();
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
						;
					questionsList.get(i).getPoints().setText(Integer.toString(exam.getQuestions().get(tempQuestion)));
					questionsList.get(i).getCheckButton().setSelected(true);
				}
			}
		}
	}

	public void cancleButtonAction() throws Exception {
		System.out.println("cancle has been pressed");
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
		// exam.setDuration(Integer.parseInt(duration.getText()));

		for (int i = 0; i < questionsList.size(); i++) {
			if (questionsList.get(i).getCheckButton().isSelected()) {
				int pointsPerQuestion = Integer.parseInt(questionsList.get(i).getPoints().getText());
				selectedQuestion.add(new QuestionInExam(pointsPerQuestion, questionArr.get(i), ++j));
				temp.put(questionArr.get(i), pointsPerQuestion);
			}
		}
		exam.setQuestions(temp);
		// exam.setCourse(courseComboBox.getValue());

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

	public void openCourselist() {
		int i;
		coursesList.clear();
		for (i = 0; i < tc.getCourses().size(); i++)
			if (tc.getCourses().get(i).getSubject().getsName().equals(subjectComboBox.getValue()))
				coursesList.add(tc.getCourses().get(i).getcName());
		courseComboBox.getItems().addAll(coursesList);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// tc.getTeacherCourse();
		// for(int i=0;i<tc.getSubjects().size();i++)
		// subjectComboBox.getItems().add(tc.getSubjects().get(i).getsName());
		// coursesList = FXCollections.observableArrayList();

	}

	private void setObservableListForTable() {
		questionArr = tc.getAllQuestions();
		questionsList = FXCollections.observableArrayList();
		for (int i = 0; i < questionArr.size(); i++) {
			CheckBox cb = new CheckBox();
			TextField tf = new TextField();
			cb.setVisible(true);
			tf.setVisible(true);
			QuestionGUI qgui = new QuestionGUI(questionArr.get(i).getQuestionID(), questionArr.get(i).getTeacherName(),
					questionArr.get(i).getQuestionTxt(), cb, tf);
			questionsList.add(qgui);
		}
		table.setItems(questionsList);
	}
}
