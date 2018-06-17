package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.Question;
import logic.TeacherController;

public class QuestionRepositoryGUI implements Initializable {
	@FXML
	private TableView<QuestionGUI> table;

	@FXML
	private TableColumn<QuestionGUI, String> questionID;
	@FXML
	private TableColumn<QuestionGUI, String> QuestionTxt;
	@FXML
	private TableColumn<QuestionGUI, String> teacherName;
	@FXML
	private TableColumn<QuestionGUI, ImageView> labelCol;
	@FXML
	private Button editQuestionButton;
	@FXML
	private Button insert;
	@FXML
	private ImageView image;
	private ArrayList<Question> arr;

	ObservableList<QuestionGUI> questionList;
	ClientConsole client;

	TeacherController tc;

	public void insertButtonAction(ActionEvent ae) throws IOException {
		System.out.println("question is added");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NewQuestion.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		NewQuestionGUI nqg = loader.getController();
		nqg.initData();
		Stage window = (Stage) insert.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	public void deleteQuestionButtonAction(ActionEvent ae) {
		System.out.println("question is deleted");
		QuestionGUI q = (QuestionGUI) table.getSelectionModel().getSelectedItem();
		Question qToDel = new Question();
		qToDel.setQuestionID(q.getQuestionID());

		Boolean b = tc.deleteQuestion(qToDel);
		if (b == true) {
			for (int i = 0; i < questionList.size(); i++)
				if (questionList.get(i).getQuestionID().equals(qToDel.getQuestionID())) {
					questionList.remove(i);
					break;
				}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("error");
			alert.setHeaderText("Error: the selected question can't be deleted beacuse it assigned to exam");
			alert.showAndWait();
		}

	}

	public void editQuestion() throws IOException {
		QuestionGUI q = (QuestionGUI) table.getSelectionModel().getSelectedItem();
		if (q == null)
			return;

		Question qToEdit = new Question();
		qToEdit.setQuestionID(q.getQuestionID());
		for (int i = 0; i < arr.size(); i++)
			if (arr.get(i).getQuestionID().equals(qToEdit.getQuestionID())) {
				qToEdit.setQuestionTxt(arr.get(i).getQuestionTxt());
				qToEdit.setTeacherID(arr.get(i).getTeacherName());
				qToEdit.setCourseList(arr.get(i).getCourseList());
				qToEdit.setAnswers(arr.get(i).getAnswers());
				qToEdit.setCorrectAnswer(arr.get(i).getCorrectAnswer());
				qToEdit.setInstruction(arr.get(i).getInstruction());
				qToEdit.setTeacherName(arr.get(i).getTeacherName());
				break;
			}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionDetails.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionDetailsGUI qdg = loader.getController();
		qdg.initData(qToEdit);
		Stage window = (Stage) editQuestionButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
		System.out.println("question is changed");
	}

	public void editQuestionButtonAction(ActionEvent ae) throws Exception {
		editQuestion();
	}

	public void questionTableMouseClicked(MouseEvent me) throws IOException {
		if (me.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null) {
			editQuestion();
		}
	}

	public QuestionRepositoryGUI() {
		client = new ClientConsole();

		tc = new TeacherController();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void initData() {

		arr = tc.getAllQuestions();
		questionList = FXCollections.observableArrayList();

		for (int i = 0; i < arr.size(); i++) {
			ImageView im = new ImageView(new Image("/images/questionIcon.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			QuestionGUI qgui = new QuestionGUI(arr.get(i).getQuestionID(), arr.get(i).getTeacherName(),
					arr.get(i).getQuestionTxt(), im);
			questionList.add(qgui);
		}
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionID"));
		teacherName.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("teacherName"));
		QuestionTxt.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionTxt"));
		labelCol.setCellValueFactory(new PropertyValueFactory<QuestionGUI, ImageView>("image"));
		table.setItems(questionList);

	}
}
