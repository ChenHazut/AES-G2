package server;

import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import common.Message;
import logic.User;
import ocsf.server.ConnectionToClient;

public class StubDBHandler implements IDBHandler {
	ArrayList<User> users;

	public StubDBHandler() {
		users = new ArrayList<User>();
		users.add(new User("11111", "Donald Duck", "0000", "Teacher", "NO"));
		users.add(new User("12121", "Mickey Mouse", "1111", "Student", "NO"));
		users.add(new User("22222", "Harry Potter", "1212", "Principal", "NO"));
	}

	@Override
	public User getUserDet(String q) {
		for (User u : users) {
			if (u.getuID().equals(q))
				return u;
		}
		return new User();
	}

	@Override
	public User loginUser(String s) {
		for (User u : users) {
			if (u.getuID().equals(s)) {
				u.setIsLoggedIn("YES");
				return u;
			}
		}
		return null;
	}

	@Override
	public Object userHandler(Message m, ConnectionToClient client, Connection conn) {
		if (m.getqueryToDo().equals("getUserDetails"))
			return (getUserDet(((User) m.getSentObj()).getuID()));
		else if (m.getqueryToDo().equals("signIn"))
			return (loginUser(((User) m.getSentObj()).getuID()));
		return null;
	}

	@Override
	public void teacherHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void StudentrHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void principalHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

}
