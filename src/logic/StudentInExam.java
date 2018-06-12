package logic;

import java.io.Serializable;

public class StudentInExam implements Serializable{

	private int grade;
	private int actualdoration; //how long it takes to do the exam
	private String examID;
	private String studentID;
	//private String date; //the date of the exam
	
	public StudentInExam(String examID ,int grade ,  String studentID)
	{
		this.grade=grade;
		this.examID=examID;
		//this.date=date;
		this.studentID = studentID;
	}
	
	public void SetGrade(int grade)
	{
		this.grade=grade;
	}
	
	public int GetGrade()
	{
		return this.grade;
	}
	
	public void Set_actualdoration(int actualdoration)
	{
		this.actualdoration=actualdoration;
	}
	
	public int Get_actualdoration()
	{
		return this.actualdoration;
	}
	
	public void Set_examID(String examID)
	{
		this.examID=examID;
	}
	
	public String Get_examID()
	{
		return this.examID;
	}
	
	public void Set_studentID(String studentID)
	{
		this.studentID=studentID;
	}
	
	public String Get_studentID()
	{
		return this.studentID;
	}
	
	/*public void Set_date(String date)
	{
		this.date=date;
	}
	
	public String Get_date()
	{
		return this.date;
	}*/
	
	
}
