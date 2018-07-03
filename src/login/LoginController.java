package login;

import client.ClientConsole;
import common.Message;
import logic.User;
import ocsf.server.ConnectionToClient;
import server.IDBHandler;

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
	private IDBHandler handler;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default constructor
	 */
	public LoginController() {
		if (LoginGUI.flag)
			this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port);
		else
			this.client = new ClientConsole("localhost", 5555);
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

	public Boolean checkUserDetails(User u) {
		if (u.getuID() == null || u.getPassword() == null)
			return false;
		return true;

	}

	public Boolean checkPassword(User u, User userToCompare) throws IllegalArgumentException {
		if (!u.getuID().equals(userToCompare.getuID()))
			throw new IllegalArgumentException("ID of userToCompare is diffrent from ID of user");
		if (u.getPassword().equals(userToCompare.getPassword()))
			return true;
		return false;
	}

	/**
	 * sent message to db to get user details and check if user id exist
	 * 
	 * @return
	 */
	public User checkIfUserIDExist(User u) {

		Message userMsg = new Message();
		userMsg.setSentObj(u);
		userMsg.setqueryToDo("getUserDetails");
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
			return null;
		return user;
	}

	/**
	 * this method send message to server to login the user that recieved in the
	 * constructor
	 * 
	 * @return
	 */
	public User loginUser() {
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
		msg = client.getMessage();
		user = (User) msg.getReturnObj();
		return user;

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
	 * return the logged user details
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

}
