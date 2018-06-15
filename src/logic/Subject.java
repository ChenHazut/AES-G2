package logic;

import java.io.Serializable;

public class Subject implements Serializable
{
	String subjectID;
	String sName;

	public Subject(String subjectID, String sName) {
		super();
		this.subjectID = subjectID;
		this.sName = sName;
	}

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	@Override
	public String toString() {
		return sName;
	}

	
	
	
	
}
