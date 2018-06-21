package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import common.Message;
import gui.QuestionInExam;

public class CheckExam {

	String examID;
	int executionID;
	
	ArrayList<StudentInExam> studentToCheck;
	
	public void getStudentsToCheck(String examID, int executionID){
		Message msg = new Message();
		
		studentToCheck = (ArrayList<StudentInExam>)msg.getReturnObj();
		
	}
	
	public void checkExams(ArrayList<StudentInExam> arr) {
		ArrayList<Integer> studentResults = new ArrayList<Integer> ();
		for(int i=0; i<arr.size(); i++) {
			StudentInExam student = arr.get(i);
			Integer grade=0;
			Integer correctAns=0;
			Integer wrongAns=0;
			for (Map.Entry<QuestionInExam, Integer> entry : student.getCheckedAnswers().entrySet()) {
				if(entry.getKey().getQuestion().getCorrectAnswer()==entry.getValue()) {
					grade+=entry.getKey().getPointsInExam();
					correctAns++;
				}
				else
					wrongAns++;
			}
			studentResults.add(grade);
			studentResults.add(correctAns);
			studentResults.add(wrongAns);
		}
		
	}
	
	public void checkForCopies(ArrayList<StudentInExam> arr) {
		HashSet<StudentInExam> studentCopied = new HashSet<StudentInExam> ();
		for(int i=0; i<arr.size(); i++) {
			StudentInExam student1 = arr.get(i);
			Integer wrongAns1 = student1.getNumberOfWrongAnswer();
			for(int j=0; j<arr.size(); j++) {
				StudentInExam student2 = arr.get(j);
				Integer wrongAns2 = student2.getNumberOfWrongAnswer();
				if(i!=j && wrongAns1==wrongAns2 && wrongAns1 > 3) {
					studentCopied.add(student1);
					studentCopied.add(student2);
				}
			}
			
		}
		
	}
	
	
	
	
	
}
