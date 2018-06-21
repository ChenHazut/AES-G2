package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.ClientConsole;
import logic.ExamInExecution;
import logic.OvertimeDetails;
import logic.Question;
import logic.StudentController;
import logic.StudentInExam;

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
	private Button cancleButton;
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
	@FXML
	private Label gradeLabel;
	ExamInExecution exam;
	Integer countPassedTime;
	Integer currSeconds;
	Thread thrd;
	Thread lockThread;
	StudentInExam s;
	private StudentController st;
	private ClientConsole client;
	Boolean flag = false;

	public void initData(ExamInExecution exam, Boolean studentSolveExam, int grade) {
		st = new StudentController();
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		countPassedTime = 0;
		this.exam = exam;
		exeTeacherName.setText(exam.getExecTeacher().getuName());
		duration.setText(Integer.toString(exam.getExamDet().getDuration()));// לשנות לזמן בפועל
		instructions.setText(exam.getExamDet().getInstructionForStudent());
		courseName.setText(exam.getExamDet().getCourse().getcName());
		ArrayList<Question> selectedQuestion = new ArrayList<Question>(); // to save all the array q
		observableQuestions = FXCollections.observableArrayList();
		if (exam.getExamDet().getQuestions() != null)
			selectedQuestion = exam.getExamDet().getQuestionsArrayList(); // took all the questions from the execution
		int j = 0;
		for (int i = 0; i < selectedQuestion.size(); i++) {
			int pointsPerQuestion = exam.getExamDet().getQuestions().get(selectedQuestion.get(i));
			observableQuestions.add(new QuestionInExam(pointsPerQuestion, selectedQuestion.get(i), ++j));
		}
		listView.setItems(observableQuestions);
		if (studentSolveExam) {
			listView.setCellFactory(QuestionListView -> new QuestionListViewCellForStudent<QuestionInExam>());
			disableListView.toBack();
			gradeLabel.setVisible(false);
			timer.setVisible(true);
			duration.setVisible(false);
			durationLabel.setVisible(false);
			listView.setEditable(false);
			numberMap = new TreeMap<Integer, String>();
			for (Integer i = 0; i <= 60; i++) {
				if (0 <= i && i <= 9)
					numberMap.put(i, "0" + i.toString());
				else
					numberMap.put(i, i.toString());
			}
			int duration = exam.getExamDet().getDuration();
			currSeconds = hmsToSeconds(duration / 60, duration % 60, 0);
			startCountDown();
			System.out.println("starting tocountdown");
			s = new StudentInExam();
			s.setIsComp(true);
			s.setStudentID(st.getStudent().getuID());
			s.setStudentName(st.getStudent().getuName());
			s.setExamID(exam.getExamDet().getExamID());
			s.setExecutionID(exam.getExecutionID());
			s.setStudentStatus("Started");
			st.changeStudentInExamStatus(s);

		} else {

			st.getStudentResultInExam(st.getStudent().getuID(), exam);
			listView.setEditable(false);
			submit.setDisable(true);
			listView.setCellFactory(QuestionListView -> new QuestionListViewCellForStudetResults<QuestionInExam>());
			disableListView.toFront();
			gradeLabel.setText(gradeLabel.getText() + grade);
			gradeLabel.setVisible(true);
		}
	}

	public void cancleAction(ActionEvent ae) {
		s.setStudentStatus("NotFinished");
		int actualDuration = (countPassedTime / 60) + 1;
		s.setActualDuration(actualDuration);
		st.changeStudentInExamStatus(s);

		Stage st = (Stage) submit.getScene().getWindow();
		st.close();
	}

	public void submitExamAction(ActionEvent ae) {

		thrd.stop();
		st.uploadManualExam("exam_" + exam.getExamDet().getExamID() + "_" + st.getStudent().getuID() + ".docx");
		submit.setDisable(true);
		submit.setVisible(false);
		disableListView.toFront();
		cancleButton.setVisible(false);
		int actualTime = (countPassedTime / 60) + 1;
		s.setActualDuration(actualTime);
		s.setStudentStatus("finished");
		st.changeStudentInExamStatus(s);

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
						countPassedTime++;
						if (currSeconds == 0) {
							s.setStudentStatus("NotFinished");
							int actualDuration = (countPassedTime / 60) + 1;
							s.setActualDuration(actualDuration);
							st.changeStudentInExamStatus(s);
							Stage st = (Stage) submit.getScene().getWindow();
							st.close();
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
		lockThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.getMessage().getReturnObj() instanceof String) {
						if (((String) client.getMessage().getReturnObj()).equals("examIsLocked")) {
							System.out.println("locked :)");
							thrd.stop();
							s.setStudentStatus("notFinished");
							int actualDuration = (countPassedTime / 60) + 1;
							s.setActualDuration(actualDuration);
							st.changeStudentInExamStatus(s);
							submit.setVisible(false);
							lockThread.stop();
						}
					} else if (client.getMessage().getqueryToDo() instanceof String) {
						if (((String) client.getMessage().getqueryToDo()).equals("overtimeApproved") && flag == false) {
							System.out.println("over time is approved yesss");
							flag = true;
							client.getMessage().setqueryToDo(null);
							flag = false;
							int ot = ((OvertimeDetails) client.getMessage().getReturnObj()).getRequestedTime();
							currSeconds += (ot * 60);
						}
					}
				}

			}

		});
		lockThread.start();
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
