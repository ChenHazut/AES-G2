package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class QuestionDetailsGUI implements Initializable {
	@FXML
	Label questionIDLabel;
	@FXML
	ComboBox<String> correctAnswerCombo;
	@FXML
	Button cancleButton;
	@FXML
	Button saveButton;
	@FXML
	TextArea QuestionLabel;
	@FXML
	TextArea answer1Label;
	@FXML
	TextArea answer2Label;
	@FXML
	TextArea answer3Label;
	@FXML
	TextArea answer4Label;
	@FXML
	Label teacherNameLabel;
	@FXML
	TextArea instructionLabel;

	@FXML
	Label qtxt;
	@FXML
	Label qans1;
	@FXML
	Label qans2;
	@FXML
	Label qans3;
	@FXML
	Label qans4;
	@FXML
	Label corAns;
	@FXML
	Label subjectcourseL;
	@FXML
	ComboBox<String> subjectCombo;
	@FXML
	ListView coursesList;

	ObservableList<String> cList;
	Question q;
	ClientConsole client;
	// GUImanager m;

	public QuestionDetailsGUI() {
		client = new ClientConsole();
		// m=new GUImanager();
		// q=m.getSelectedQuestion();

	}

	public void start(Stage primaryStage) throws IOException {
		System.out.println("bla bla bla bla");
		Parent root = FXMLLoader.load(getClass().getResource("editQuestion.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();

	}

	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	protected Question getFilledDetails() {
		Question updatedQuestion = new Question();

		updatedQuestion.setQuestionID(questionIDLabel.getText());
		updatedQuestion.setTeacherName(teacherNameLabel.getText());
		if (QuestionLabel.getText().equals("")) {
			qtxt.setText("*");
			return null;
		} else
			updatedQuestion.setQuestionTxt(QuestionLabel.getText());
		updatedQuestion.setInstruction(instructionLabel.getText());
		if (answer1Label.getText().equals("")) {
			qans1.setText("*");
			return null;
		}
		if (answer2Label.getText().equals("")) {
			qans2.setText("*");
			return null;
		}
		if (answer3Label.getText().equals("")) {
			qans3.setText("*");
			return null;
		}
		if (answer4Label.getText().equals("")) {
			qans4.setText("*");
			return null;
		}
		updatedQuestion.setAnswers(answer1Label.getText(), answer2Label.getText(), answer3Label.getText(),
				answer4Label.getText());
		updatedQuestion.setCorrectAnswer(Integer.parseInt(correctAnswerCombo.getValue()));
		return updatedQuestion;
	}

	public void saveButtonAction(ActionEvent ae) throws Exception {

		System.out.println("save has been pressed");

		Question updatedQuestion = getFilledDetails();
		if (updatedQuestion == null)
			return;
		TeacherController tc = new TeacherController();
		tc.editQuestion(updatedQuestion);
		q = updatedQuestion;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionRepositoryGUI qrg = loader.getController();
		qrg.initData();
		Stage window = (Stage) saveButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void cancleButtonAction(ActionEvent ae) throws Exception {
		System.out.println("cancle has been pressed");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionRepositoryGUI qrg = loader.getController();
		qrg.initData();
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
		// Stage stage = (Stage) cancleButton.getScene().getWindow();
		// m.setSelectedQuestion(null);
		// QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		// qrg.start(stage);
	}

	public void correctAnswerTextField(ActionEvent ae) {

	}

	public void initData(Question qToEdit) {
		q = qToEdit;
		questionIDLabel.setText(q.getQuestionID());
		questionIDLabel.setDisable(true);
		QuestionLabel.setText(q.getQuestionTxt());
		answer1Label.setText(q.getAnswers()[0]);
		answer2Label.setText(q.getAnswers()[1]);
		answer3Label.setText(q.getAnswers()[2]);
		answer4Label.setText(q.getAnswers()[3]);
		teacherNameLabel.setText(q.getTeacherName());
		teacherNameLabel.setDisable(true);
		instructionLabel.setText(q.getInstruction());
		correctAnswerCombo.getItems().addAll("1", "2", "3", "4");
		correctAnswerCombo.setPromptText(Integer.toString(q.getCorrectAnswer()));
		subjectCombo.getSelectionModel().select(q.getCourseList().get(0).getSubject().getsName());
		subjectCombo.setDisable(true);
		cList = FXCollections.observableArrayList();
		for (int i = 0; i < q.getCourseList().size(); i++)
			cList.add(q.getCourseList().get(i).getcName());
		coursesList.getItems().add(cList);
		coursesList.disabledProperty();

	}

}
