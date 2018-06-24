package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import gui.QuestionInExam;

public class CheckExam {

	String examID;
	int executionID;

	ArrayList<StudentInExam> studentToCheck;

	public HashMap<StudentInExam, ArrayList<Integer>> checkExams(ArrayList<StudentInExam> arr) {
		HashMap<StudentInExam, ArrayList<Integer>> studentResultsMap = new HashMap<StudentInExam, ArrayList<Integer>>();

		for (int i = 0; i < arr.size(); i++) {
			ArrayList<Integer> studentResults = new ArrayList<Integer>();
			StudentInExam student = arr.get(i);
			if (student.getGrade() == 0)
				continue;
			Integer grade = 0;
			Integer correctAns = 0;
			Integer wrongAns = 0;
			for (Map.Entry<QuestionInExam, Integer> entry : student.getCheckedAnswers().entrySet()) {
				System.out.println("Question id: " + entry.getKey().getQuestion().getQuestionID() + " selected ans: "
						+ entry.getValue() + " correct ans: " + entry.getKey().getQuestion().getCorrectAnswer());
			}
			for (Map.Entry<QuestionInExam, Integer> entry : student.getCheckedAnswers().entrySet()) {
				if (entry.getKey().getQuestion().getCorrectAnswer() == entry.getValue()) {
					grade += entry.getKey().getPointsInExam();
					correctAns++;
				} else
					wrongAns++;
			}
			studentResults.add(grade);
			System.out.println("grade of " + student.getStudentID() + " is:" + grade);
			studentResults.add(correctAns);
			studentResults.add(wrongAns);
			studentResultsMap.put(student, studentResults);
		}
		return studentResultsMap;
	}

	public HashSet<StudentInExam> checkForCopies(HashMap<StudentInExam, ArrayList<Integer>> result) {
		HashSet<StudentInExam> studentCopied = new HashSet<StudentInExam>();
		for (Map.Entry<StudentInExam, ArrayList<Integer>> entry1 : result.entrySet()) {
			StudentInExam student1 = entry1.getKey();
			Integer wrongAns1 = entry1.getValue().get(2);
			for (Map.Entry<StudentInExam, ArrayList<Integer>> entry2 : result.entrySet()) {
				StudentInExam student2 = entry2.getKey();
				Integer wrongAns2 = entry2.getValue().get(2);
				System.out.println("1: " + student1.getStudentID() + " 2: " + student2.getStudentID());
				System.out.println("1w: " + wrongAns1 + " 2w: " + wrongAns2);
				if (!(student1.getStudentID().equals(student2.getStudentID())) && wrongAns1 == wrongAns2
						&& wrongAns1 >= 3) {
					studentCopied.add(student1);
					studentCopied.add(student2);
				}
			}
		}

		return studentCopied;
	}

}
