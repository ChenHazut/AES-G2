package logic;

import java.io.Serializable;

public class User implements Serializable
{
	private String uID;       //user id
	private String uName;     //user name
	private String password;  //user password
	private String Title;
	private String isLoggedIn;   //0 if user isn't logged in already, 1 else
	

	public User(String uid, String upass) 
	{
		uID=uid;
		password=upass;
	}

	public User() 
	{
		
	}

	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsLoggedIn() {
		return isLoggedIn;
	}
	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}
	
	
}
