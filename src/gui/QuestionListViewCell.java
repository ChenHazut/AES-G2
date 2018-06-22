package gui;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import logic.Question;

public abstract class QuestionListViewCell<T> extends ListCell<QuestionInExam> {

	@FXML
	private AnchorPane pane;
	@FXML
	private Label questionNumber;
	@FXML
	RadioButton radioButton1;
	@FXML
	RadioButton radioButton2;
	@FXML
	RadioButton radioButton3;
	@FXML
	RadioButton radioButton4;
	@FXML
	private Text questionBody;
	@FXML
	private Text answerText1;
	@FXML
	private Text answerText2;
	@FXML
	private Text answerText3;
	@FXML
	private Text answerText4;
	@FXML
	private Label points;
	@FXML
	private Label correctAnswerLabel;

	private FXMLLoader mLLoader;
	final ToggleGroup group = new ToggleGroup();
	QuestionInExam qie;

	@Override
	protected void updateItem(QuestionInExam qie, boolean empty) {
		super.updateItem(qie, empty);

		StudentSelection ss = StudentSelection.getInstance();
		if (empty || qie == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (mLLoader == null) {
				mLLoader = new FXMLLoader(getClass().getResource("ExamFormQuestionCell.fxml"));
				mLLoader.setController(this);
				try {
					mLLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			Question q = (logic.Question) ((gui.QuestionInExam) qie).getQuestion();
			this.qie = (gui.QuestionInExam) qie;

			questionBody.setText(q.getQuestionTxt());
			answerText1.setText(q.getAnswers()[0]);
			answerText2.setText(q.getAnswers()[1]);
			answerText3.setText(q.getAnswers()[2]);
			answerText4.setText(q.getAnswers()[3]);
			correctAnswerLabel.setText("The correct answer is " + q.getCorrectAnswer());
			correctAnswerLabel.setVisible(this.setCorrectAnswerLabel());

			int temp = ((gui.QuestionInExam) qie).getPointsInExam();
			String s = "(" + temp + " point";
			s += (temp == 1 ? ")" : "s)");
			points.setText(s);
			questionNumber.setText(Integer.toString(((gui.QuestionInExam) qie).getSerialNumberInExam()) + ")");
			radioButton1.setToggleGroup(group);
			radioButton1.setUserData(1);
			radioButton2.setToggleGroup(group);
			radioButton1.setUserData(2);
			radioButton3.setToggleGroup(group);
			radioButton1.setUserData(3);
			radioButton4.setToggleGroup(group);
			radioButton1.setUserData(4);

			setCheckBoxInQuestion(q.getQuestionID());

			setGraphic(pane);
		}
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					System.out.println("qID: " + ss.question.getQuestion().getQuestionID());
					ss.studentAnswers.put(qie, (Integer) group.getSelectedToggle().getUserData());
				}
			}
		});

	}

	public abstract Boolean setCorrectAnswerLabel();

	public abstract void setCheckBoxInQuestion(String s);
}
