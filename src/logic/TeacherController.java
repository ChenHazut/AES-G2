package logic;

import java.util.ArrayList;

import common.Message;
import gui.LoginGUI;

public class TeacherController {
	User teacher;
	LoginController lc;
	ClientConsole client;
	ArrayList<Course> courses;
	ArrayList<Subject> subjects;
	ArrayList<Question> selectedQuestions;

	public TeacherController() {
		lc = new LoginController();
		teacher = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
	}

	public ArrayList<Question> getAllQuestions()// send request to db to get all question which belong to subject this
												// teacher teach
	{
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllQuestionRelevantToTeacher");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Question> arrOfQuestions = new ArrayList<Question>();
		arrOfQuestions = (ArrayList<Question>) msg.getReturnObj();
		return arrOfQuestions;

	}

	public String getExamID(Course course) {
		Message msg = new Message();
		msg.setSentObj(course);
		msg.setqueryToDo("getNextExamID");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();

		String str = (String) msg.getReturnObj();
		return str;
	}

	public Boolean deleteQuestion(Question qToDel) {
		Message msg = new Message();
		msg.setClassType("teacher");
		msg.setqueryToDo("deleteQuestion");
		msg.setSentObj(qToDel);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		Boolean b = (Boolean) msg.getReturnObj();
		return b;
	}

	public Question createNewQuestion(Question qToAdd) {
		Message questionToSend = new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("createQuestion");
		questionToSend.setSentObj(qToAdd);
		client.accept(questionToSend);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return (Question) client.getMessage().getReturnObj();
	}

	public void getTeacherCourse() {
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllCourseRelevantToTeacher");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Course> arrOfCourses = new ArrayList<Course>();
		arrOfCourses = (ArrayList<Course>) msg.getReturnObj();
		courses = arrOfCourses;
		subjects = new ArrayList<Subject>();
		ArrayList<String> subjectFlag = new ArrayList<String>();
		for (int i = 0; i < courses.size(); i++)
			if (!subjectFlag.contains(courses.get(i).getSubject().getSubjectID())) {
				subjectFlag.add(courses.get(i).getSubject().getSubjectID());
				subjects.add(courses.get(i).getSubject());
			}
	}

	public void editQuestion(Question updatedQuestion) {
		Message questionToSend = new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("updadeQuestion");
		questionToSend.setSentObj(updatedQuestion);
		client.accept(questionToSend);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public User getTeacher() {
		return teacher;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public Course getCourseFromName(String courseName) {
		for (int i = 0; i < courses.size(); i++) {
			String s = courses.get(i).getcName();
			if (s.equals(courseName))
				return new Course(courses.get(i));
		}
		return null;
	}

	public ArrayList<Question> getQuestionsForTeacherInCourse(Course course) {
		Message msg = new Message();
		msg.setqueryToDo("QuestionForTeacherInCourse");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg.setSentObj(course);
		msg = client.getMessage();
		ArrayList<Question> questionsInCourse = (ArrayList<Question>) msg.getReturnObj();
		return questionsInCourse;

	}

	public User teacherDet(String uID) {
		Message msg = new Message();
		msg.setSentObj(uID);
		msg.setqueryToDo("getTeacherDetails");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		User teacher = (User) msg.getReturnObj();
		return teacher;
	}

	public void deleteExam(Exam eToDel) {
		Message msg = new Message();
		msg.setClassType("teacher");
		msg.setqueryToDo("deleteExam");
		msg.setSentObj(eToDel);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Exam> getAllExams() {
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllExamsRelevantToTeacher");
		msg.setClassType("Teacher");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Exam> arrOfExams = new ArrayList<Exam>();
		arrOfExams = (ArrayList<Exam>) msg.getReturnObj();
		return arrOfExams;
	}

	public ArrayList<ExamInExecution> getAllExamsInExecutionForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<ExamInExecution> arrOfExams = new ArrayList<ExamInExecution>();
		arrOfExams = (ArrayList<ExamInExecution>) msg.getReturnObj();
		return arrOfExams;
	}

	public ArrayList<User> getAllStudentsInCourse(String sID, String cID) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getStudentsInCourse");
		Course c = new Course(cID, sID);
		msg.setSentObj(c);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<User> arrOfstudents = new ArrayList<User>();
		arrOfstudents = (ArrayList<User>) msg.getReturnObj();
		return arrOfstudents;
	}

	public ExamInExecution executeNewExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("executeNewExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution examInExec = new ExamInExecution();
		examInExec = (ExamInExecution) msg.getReturnObj();
		return examInExec;
	}

	public ArrayList<StudentInExam> getExamnieesOfExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getExamnieesOfExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<StudentInExam> sList = new ArrayList<StudentInExam>();
		sList = (ArrayList<StudentInExam>) msg.getReturnObj();
		return sList;
	}

	public void lockExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("lockExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<ExamInExecution> getLockedExamsForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllLockedExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<ExamInExecution> arrOfExams = new ArrayList<ExamInExecution>();
		arrOfExams = (ArrayList<ExamInExecution>) msg.getReturnObj();
		return arrOfExams;
	}

	public ArrayList<ExamInExecution> getWrittenExamsForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllWrittenExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<ExamInExecution> arrOfExams = new ArrayList<ExamInExecution>();
		arrOfExams = (ArrayList<ExamInExecution>) msg.getReturnObj();
		return arrOfExams;
	}

	public ArrayList<Question> getSelectedQuestions() {
		return selectedQuestions;
	}

	public void setSelectedQuestions(ArrayList<Question> array) {
		this.selectedQuestions = array;
	}

	public void saveExam(Exam exam) {
		Message msg = new Message();
		msg.setSentObj(exam);
		msg.setClassType("Teacher");
		msg.setqueryToDo("saveExamToDB");
		client.accept(msg);
	}
}
