package logic;

import common.Message;
import gui.LoginGUI;
import ocsf.server.ConnectionToClient;

/**
 * this class controls all interaction about the login.
 * 
 * @author chen1
 *
 */
public class LoginController {
	// **************************************************
	// Fields
	// **************************************************
	User userToSend;
	static User user;
	ClientConsole client;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * constructor called from the logingui
	 */
	public LoginController(User u) {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		userToSend = u;
	}

	/**
	 * Default constructor
	 */
	public LoginController() {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
	}

	/**
	 * saves in server the map of connected users and the connection to each user
	 * 
	 * @return
	 */
	public ConnectionToClient getClientConnection() {
		Message msg = new Message();
		msg.setSentObj(null);
		msg.setqueryToDo("getConnection");
		msg.setClassType("User");
		client.accept(msg);

		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		return (ConnectionToClient) msg.getReturnObj();
	}

	/**
	 * sent message to db to get user details and check if user id exist
	 * 
	 * @return
	 */
	public boolean checkIfUserIDExist() {
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

	/**
	 * this method send message to server to login the user that recieved in the
	 * constructor
	 */
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
		client.printAllConnectedUsers();

	}

	/**
	 * this method send message to server to logout the user that is logged in
	 */
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

	/**
	 * return the user password
	 * 
	 * @return
	 */
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * return true if the user is logged in
	 * 
	 * @return
	 */
	public boolean isConnected() {
		if (user.getIsLoggedIn().equals("NO"))
			return false;
		return true;
	}

	/**
	 * return user title
	 * 
	 * @return
	 */
	public String getTitle() {
		return user.getTitle();
	}

	/**
	 * return the logged user details
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

}
