package server;

import java.io.IOException;
import java.sql.SQLException;
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
	public void searchUserInDB(Message msg, ConnectionToClient client, Connection conn)
			throws SQLException, IOException {

	}

}
