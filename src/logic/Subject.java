package logic;

import java.io.Serializable;

public class Subject implements Serializable
{
	String subjectID;
	String sName;
	public static int nextQID=4;
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

	public String getNextQID() 
	{
		String s= Integer.toString(nextQID);
		nextQID++;
		if(nextQID<10)
			return "00"+s;
		else if(nextQID<100)
			return "0"+s;
		else return s;
	}
	
	
	
}
