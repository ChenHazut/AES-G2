package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.Exam;
import logic.ExamInExecution;
import logic.StudentController;

public class ManuallyExamGUI implements Initializable {

	ExamInExecution exam;
	@FXML
	Button downloadExam;
	@FXML
	Button uploadExam;
	StudentController st;

	public void downloadExamAction(ActionEvent ae) throws IOException {

		Exam e = st.getExamByExamID(exam);
		CreateDocument createWord = new CreateDocument(e);
		createWord.createWordExam();
	}

	public void uploadExamAction(ActionEvent ae) {
		st.uploadManualExam("exam_" + exam.getExamDet().getExamID());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initData(ExamInExecution examInExecution) {
		st = new StudentController();
		exam = examInExecution;

	}

}
