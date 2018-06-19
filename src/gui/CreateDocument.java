package gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import logic.Exam;
import logic.Question;

public class CreateDocument {

	Exam exam;

	public CreateDocument(Exam exam) {
		this.exam = exam;
	}

	public void createWordExam() throws IOException {
		// Blank Document
		XWPFDocument document = new XWPFDocument();
		Iterator it = exam.getQuestions().entrySet().iterator();
		int i = 0;
		XWPFParagraph paragraph1 = document.createParagraph();
		paragraph1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run1 = paragraph1.createRun();
		run1.setText("ExamID: " + exam.getExamID());
		run1.addBreak();
		run1.setText("Course Name: " + exam.getCourseName());
		run1.addBreak();
		if (exam.getInstructionForStudent() != null) {
			run1.setText("instructions: " + exam.getInstructionForStudent());
			run1.addBreak();
		}
		run1.setText("please select the correct answer by writing V in the [   ] next to the correct answer");
		run1.addBreak();

		while (it.hasNext()) {
			i++;
			Map.Entry pair = (Map.Entry) it.next();
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.RIGHT);
			XWPFRun run = paragraph.createRun();
			run.setText("(" + pair.getValue().toString() + " points)");
			run.addBreak();
			run.setText(i + ". " + ((Question) pair.getKey()).getQuestionTxt());

			for (int j = 0; j < 4; j++) {
				run.addBreak();
				run.setText("   [ ]" + "(" + (j + 1) + ") " + ((Question) pair.getKey()).getAnswers()[j]);
			}
			run.addBreak();
			run.addBreak();
		}

		XWPFParagraph paragraph2 = document.createParagraph();
		paragraph2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run2 = paragraph2.createRun();
		run2.setText("Good Luck!");
		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File("./exams/exam_" + exam.getExamID() + ".docx"));
		document.write(out);
		out.close();
	}

	public static void main(String[] args) {

	}

}