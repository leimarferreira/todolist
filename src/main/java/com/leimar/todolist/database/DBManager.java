package com.leimar.todolist.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {
	
	private Connection connection;
	private PreparedStatement statement;
	
	public DBManager() {
		try {
			connection = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void createDatabase() {
    	this.createTasksTable();
    }
    
    private void createTasksTable() {
    	final String SQL = "CREATE TABLE tasks("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + "name VARCHAR(50) NOT NULL,"
                + "description VARCHAR(150),"
                + "start_date INT,"
                + "end_date INT,"
                + "is_done INT NOT NULL DEFAULT 0)";
    	
    	try {
			statement = connection.prepareStatement(SQL);
			statement.execute();
		} catch (SQLException e) {
			
		}
    }
    
    public void close() {
    	try {
    		if (statement != null && !statement.isClosed()) {
    			statement.close();
    		}
    		
    		if (connection != null && !connection.isClosed()) {
    			connection.close();
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
