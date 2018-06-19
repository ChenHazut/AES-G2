package logic;

import common.Message;
import gui.LoginGUI;

public class LoginController {
	User userToSend;
	static User user;
	ClientConsole client;

	public LoginController(User u) {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		userToSend = u;
	}

	public LoginController() {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
	}

	public boolean checkIfUserIDExist() // sent message to db to get user details and check if user id exist
	{
		Message userMsg = new Message();
		userMsg.setSentObj(userToSend);
		userMsg.setqueryToDo("checkIfUserExist");
		userMsg.setClassType("User");
		client.accept(userMsg);

		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userMsg = client.getMessage();
		user = (User) userMsg.getReturnObj();
		if (user.getuName() == null)
			return false;
		return true;
	}

	public void loginUser() {
		Message msg = new Message();
		msg.setClassType("user");
		msg.setqueryToDo("signIn");
		user.setIsLoggedIn("YES");
		msg.setSentObj(user);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void logoutUser() {
		Message msg = new Message();
		msg.setClassType("user");
		msg.setqueryToDo("logout");
		user.setIsLoggedIn("NO");
		msg.setSentObj(user);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getPassword() {
		return user.getPassword();
	}

	public boolean isConnected() {
		if (user.getIsLoggedIn().equals("NO"))
			return false;
		return true;
	}

	public String getTitle() {
		return user.getTitle();
	}

	public User getUser() {
		return user;
	}

}
