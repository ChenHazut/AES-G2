package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	private String QuestionTxt;
	private String teacherID;
	private String questionID;
	private String[] answers;
	private int correctAnswer;
	private String instruction;
	private String teacherName;
	private ArrayList<Course> courseList;

	public Question(String questionTxt, String questionID, String teacherId, String instruction, String answer1,
			String answer2, String answer3, String answer4, int correctAnswer) {
		this.QuestionTxt = questionTxt;
		this.teacherID = teacherId;
		this.questionID = questionID;
		this.answers = new String[4];
		this.answers[0] = answer1;
		this.answers[1] = answer2;
		this.answers[2] = answer3;
		this.answers[3] = answer4;
		this.correctAnswer = correctAnswer;
		this.instruction = instruction;
		courseList = new ArrayList<Course>();
	}

	public Question(Question q) {
		this(q.getQuestionTxt(), q.getQuestionID(), q.getTeacherID(), q.getInstruction(), q.getAnswers()[0],
				q.getAnswers()[1], q.getAnswers()[2], q.getAnswers()[3], q.getCorrectAnswer());
	}

	public Question() {
		this.answers = new String[4];
		courseList = new ArrayList<Course>();
	}

	public String getQuestionTxt() {
		return QuestionTxt;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public String getQuestionID() {
		return questionID;
	}

	public String getAnswerI(int i) {
		return answers[i];
	}

	public String[] getAnswers() {
		return answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setQuestionTxt(String questionTxt) {
		QuestionTxt = questionTxt;
	}

	public void setTeacherID(String teacherId) {
		this.teacherID = teacherId;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public void setAnswers(String answer1, String answer2, String answer3, String answer4) {
		this.answers[0] = answer1;
		this.answers[1] = answer2;
		this.answers[2] = answer3;
		this.answers[3] = answer4;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;

	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public String toString() {
		String s = "";
		// s+="Teacher name: "+getTeacherName() +" Teacher id: "+getTeacherID();
		s += "\nQuestion ID: " + getQuestionID() + "\nInstructions: " + getInstruction() + "\nQuestion Body: "
				+ getQuestionTxt() + "\n";
		for (int i = 0; i < answers.length; i++)
			s += "\tanswer " + (i + 1) + ": " + answers[i] + "\n";
		s += "The correct answer is: " + getCorrectAnswer();
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionID == null) ? 0 : questionID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionID == null) {
			if (other.questionID != null)
				return false;
		} else if (!questionID.equals(other.questionID))
			return false;
		return true;
	}

}
