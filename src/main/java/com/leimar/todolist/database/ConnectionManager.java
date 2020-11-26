package com.leimar.todolist.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private static final String URL = "jdbc:sqlite:database.db";

	public static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(URL);

		return connection;
	}
}
