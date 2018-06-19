package logic;

import java.io.Serializable;

public class Course implements Serializable {
	String cID;
	String cName;
	Subject subject;
	String teacherID;

	public Course(String cID, String cName, String teacherID, Subject s) {
		super();
		this.cID = cID;
		this.cName = cName;
		this.teacherID = teacherID;
		subject = s;
	}

	public Course(Course course) {
		this(course.getcID(), course.getcName(), course.getcName(), course.getSubject());
	}

	public Course(String cID, String sID) {
		this.cID = cID;
		subject = new Subject(sID, null);
	}

	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	@Override
	public String toString() {
		return cName;
	}

}
