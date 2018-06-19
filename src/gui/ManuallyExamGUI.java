package gui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import logic.Exam;
import logic.ExamInExecution;
import logic.StudentController;

public class ManuallyExamGUI {

	ExamInExecution exam;
	@FXML
	Button downloadExam;
	@FXML
	Button uploadExam;

	@FXML
	TextField examID;

	@FXML
	TextField courseNameTF;

	@FXML
	private Text hoursTimer;

	@FXML
	private Text minutesTimer;

	@FXML
	private ImageView uploadImage;

	@FXML
	private Text secondTimer;
	Map<Integer, String> numberMap;
	Integer currSeconds;
	Thread thrd;
	StudentController st;

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
							uploadExam.setDisable(true);
							uploadImage.setVisible(false);
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

	public void downloadExamAction(ActionEvent ae) throws IOException {

		Exam e = st.getExamByExamID(exam);
		CreateDocument createWord = new CreateDocument(e);
		createWord.createWordExam();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		startCountDown();
	}

	public void uploadExamAction(ActionEvent ae) {
		thrd.stop();
		st.uploadManualExam("exam_" + exam.getExamDet().getExamID());
		uploadExam.setDisable(true);
		uploadImage.setVisible(false);

	}

	public void initData(ExamInExecution examInExecution) {
		st = new StudentController();
		exam = examInExecution;
		examID.setText(examInExecution.getExamDet().getExamID());
		courseNameTF.setText(examInExecution.getCourseName());
		numberMap = new TreeMap<Integer, String>();
		for (Integer i = 0; i <= 60; i++) {
			if (0 <= i && i <= 9)
				numberMap.put(i, "0" + i.toString());
			else
				numberMap.put(i, i.toString());
		}
		int duration = examInExecution.getExamDet().getDuration();
		currSeconds = hmsToSeconds(duration / 60, duration % 60, 0);
		setOutput();

	}

}
