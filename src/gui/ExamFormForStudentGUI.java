package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
	@FXML
	private Label durationLabel;

	Map<Integer, String> numberMap;
	@FXML
	private Text hoursTimer;

	@FXML
	private Text minutesTimer;

	@FXML
	private Text secondTimer;
	@FXML
	private GridPane timer;

	Integer currSeconds;
	Thread thrd;

	public void initData(ExamInExecution exam, Boolean studentSolveExam) {

		exeTeacherName.setText(exam.getExecTeacher().getuName());
		duration.setText(Integer.toString(exam.getExamDet().getDuration()));// לשנות לזמן בפועל
		instructions.setText(exam.getExamDet().getInstructionForStudent());
		courseName.setText(exam.getExamDet().getCourse().getcName());
		ArrayList<Question> selectedQuestion = new ArrayList<Question>(); // to save all the array q
		observableQuestions = FXCollections.observableArrayList();
		if (exam.getExamDet().getQuestions() != null)
			selectedQuestion = exam.getExamDet().getQuestionsArrayList(); // took all the questions from the execution
																			// exam
		else
			selectedQuestion = exam.getExamDet().getQuestionArr();
		int j = 0;
		for (int i = 0; i < selectedQuestion.size(); i++) {
			int pointsPerQuestion = exam.getExamDet().getQuestions().get(selectedQuestion.get(i));
			observableQuestions.add(new QuestionInExam(pointsPerQuestion, selectedQuestion.get(i), ++j));
		}

		if (!studentSolveExam) {
			timer.setVisible(true);
			duration.setVisible(false);
			durationLabel.setVisible(false);
			listView.setEditable(false);
			submit.setDisable(true);
			numberMap = new TreeMap<Integer, String>();
			for (Integer i = 0; i <= 60; i++) {
				if (0 <= i && i <= 9)
					numberMap.put(i, "0" + i.toString());
				else
					numberMap.put(i, i.toString());
			}
			int duration = exam.getExamDet().getDuration();
			currSeconds = hmsToSeconds(duration / 60, duration % 60, 0);
			setOutput();
		}

		listView.setItems(observableQuestions);
		listView.setCellFactory(QuestionListView -> new QuestionListViewCellForStudent<QuestionInExam>());

	}

	public Integer hmsToSeconds(Integer h, Integer m, Integer s) {
		Integer hToSeconds = h * 3600;
		Integer mToSeconds = m * 60;
		Integer total = hToSeconds + mToSeconds + s;
		return total;
	}

	void startCountDown() {
		thrd = new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					while (true) {
						setOutput();
						Thread.sleep(1000);
						if (currSeconds == 0) {

							thrd.stop();
						}
						currSeconds -= 1;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		thrd.start();
	}

	void setOutput() {
		LinkedList<Integer> currHms = secondsToHms(currSeconds);
		hoursTimer.setText(numberMap.get(currHms.get(0)));
		minutesTimer.setText(numberMap.get(currHms.get(1)));
		secondTimer.setText(numberMap.get(currHms.get(2)));

	}

	LinkedList<Integer> secondsToHms(Integer currSecond) {
		Integer hours = currSecond / 3600;
		currSecond = currSecond % 3600;
		Integer minutes = currSecond / 60;
		currSecond = currSecond % 60;
		Integer seconds = currSecond;
		LinkedList<Integer> answer = new LinkedList<Integer>();
		answer.add(hours);
		answer.add(minutes);
		answer.add(seconds);
		return answer;
	}

}
