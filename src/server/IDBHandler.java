package server;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import common.Message;
import logic.User;
import ocsf.server.ConnectionToClient;

public interface IDBHandler {
	public User getUserDet(String q);

	public User loginUser(String s);

	public Object userHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void teacherHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void StudentrHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void principalHandler(Message m, ConnectionToClient client, Connection conn)
			throws SQLException, IOException;
}
