package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ExamReport implements Serializable {

	private String examID;
	private int executionID;
	private int[] percentages;
	public final String[] headers = { "0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90",
			"91-100" };
	private float avg;
	private int midean;

	public ExamReport() {

	}

	public ExamReport(ArrayList<Integer> gradeArr, String examID, int executionID) {
		this.examID = examID;
		this.executionID = executionID;
		int sum = 0;
		int[] per = new int[10];
		for (int i = 0; i < gradeArr.size(); i++) {
			int grade = gradeArr.get(i);
			sum += grade;
			if (0 <= grade && grade <= 10)
				per[0]++;
			if (11 <= grade && grade <= 20)
				per[1]++;
			if (21 <= grade && grade <= 30)
				per[2]++;
			if (31 <= grade && grade <= 40)
				per[3]++;
			if (41 <= grade && grade <= 50)
				per[4]++;
			if (51 <= grade && grade <= 60)
				per[5]++;
			if (61 <= grade && grade <= 70)
				per[6]++;
			if (71 <= grade && grade <= 80)
				per[7]++;
			if (81 <= grade && grade <= 90)
				per[8]++;
			if (91 <= grade && grade <= 100)
				per[9]++;
		}
		Collections.sort(gradeArr);
		this.percentages = per;
		midean = gradeArr.get(gradeArr.size() / 2);
		avg = (float) sum / gradeArr.size();
	}

	public int[] getPercentages() {
		return percentages;
	}

	public void setPercentages(int[] percentages) {
		this.percentages = percentages;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public int getMidean() {
		return midean;
	}

	public void setMidean(int midean) {
		this.midean = midean;
	}

	public int getExecutionID() {
		return executionID;
	}

	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	public String getExamID() {
		return examID;
	}

	public void setExamID(String examID) {
		this.examID = examID;
	}

}
