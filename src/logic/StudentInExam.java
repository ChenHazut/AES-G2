package logic;

public class StudentInExam {

	private int grade;
	private int actualdoration; //how long it takes to do the exam
	private int examID;
	private String studentID;
	private String date; //the date of the exam
	
	public StudentInExam(int examID ,int grade , String date, String studentID)
	{
		this.grade=grade;
		this.examID=examID;
		this.date=date;
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
	
	public void Set_examID(int examID)
	{
		this.examID=examID;
	}
	
	public int Get_examID()
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
	
	public void Set_date(String date)
	{
		this.date=date;
	}
	
	public String Get_date()
	{
		return this.date;
	}
	
	
}
