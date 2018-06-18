package gui;

import javafx.scene.control.CheckBox;
import logic.User;

public class UserRow 
{
	private User user;
	private String userID;
	private String userName;
	private CheckBox check;
	
	public UserRow(User user,CheckBox c) {
		super();
		this.user=user;
		this.userID = user.getuID();
		this.userName = user.getuName();
		check=c;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public CheckBox getCheck() {
		return check;
	}
	public void setCheck(CheckBox check) {
		this.check = check;
	}
	
	
}
