package server;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import common.Message;
import ocsf.server.ConnectionToClient;

public interface IDBHandler {
	void searchUserInDB(Message msg, ConnectionToClient client, Connection conn) throws SQLException, IOException;
}
