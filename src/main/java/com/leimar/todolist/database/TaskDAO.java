package com.leimar.todolist.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.leimar.todolist.Task;

public class TaskDAO {

	private Connection connection;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public TaskDAO() {
		try {
			connection = ConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Task> getAllTasks() {
		final String SQL = "SELECT * FROM tasks";

		LinkedList<Task> tasks = null;

		try {
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			tasks = new LinkedList<>();

			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("description"));
				task.setStartDate(resultSet.getLong("start_date"));
				task.setEndDate(resultSet.getLong("end_date"));
				int isDone = resultSet.getInt("is_done");
				if (isDone == 1) {
					task.toogleDone();
				}
				tasks.add(task);
			}
		} catch (SQLException e) {
			DBManager dbManager = new DBManager();
			dbManager.createDatabase();
			dbManager.close();
		}

		return tasks;
	}

	public List<Task> getDoneTasks() {
		final String SQL = "SELECT * FROM tasks WHERE is_done = ?";

		LinkedList<Task> tasks = null;

		try {
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, 1);
			resultSet = statement.executeQuery();
			tasks = new LinkedList<>();

			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("description"));
				task.setStartDate(resultSet.getLong("start_date"));
				task.setEndDate(resultSet.getLong("end_date"));
				task.toogleDone();
				tasks.add(task);
			}
		} catch (SQLException e) {
			DBManager dbManager = new DBManager();
			dbManager.createDatabase();
			dbManager.close();
		}

		return tasks;
	}

	public List<Task> getUndoneTasks() {
		final String SQL = "SELECT * FROM tasks WHERE is_done = ?";

		LinkedList<Task> tasks = null;

		try {
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, 0);
			resultSet = statement.executeQuery();
			tasks = new LinkedList<>();

			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setDescription(resultSet.getString("description"));
				task.setStartDate(resultSet.getLong("start_date"));
				task.setEndDate(resultSet.getLong("end_date"));
				tasks.add(task);
			}
		} catch (SQLException e) {
			DBManager dbManager = new DBManager();
			dbManager.createDatabase();
			dbManager.close();
		}

		return tasks;
	}

	public void insertTask(Task task) {
		final String SQL = "INSERT INTO tasks (name, description, start_date, "
				+ "end_date, is_done) VALUES (?, ?, ?, ?, ?)";

		try {
			statement = connection.prepareStatement(SQL);
			statement.setString(1, task.getName());

			if (task.getDescription() != null) {
				statement.setString(2, task.getDescription());
			} else {
				statement.setNull(2, Types.NULL);
			}

			statement.setLong(3, task.getStartInEpochDay());
			statement.setLong(4, task.getEndDateInEpochDay());

			if (task.isDone()) {
				statement.setInt(5, 1);
			} else {
				statement.setInt(5, 0);
			}

			statement.execute();
			assignId(task);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTask(Task task) {
		final String SQL = "UPDATE tasks SET name = ?," + "description = ?, start_date = ?, "
				+ "end_date = ?, is_done = ? WHERE id = ?";

		try {
			statement = connection.prepareStatement(SQL);
			statement.setString(1, task.getName());

			if (task.getDescription() != null) {
				statement.setString(2, task.getDescription());
			} else {
				statement.setNull(2, Types.NULL);
			}

			statement.setLong(3, task.getStartInEpochDay());
			statement.setLong(4, task.getEndDateInEpochDay());

			if (task.isDone()) {
				statement.setInt(5, 1);
			} else {
				statement.setInt(5, 0);
			}

			statement.setInt(6, task.getId());

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeTask(Task task) {
		final String SQL = "DELETE FROM tasks WHERE id = ?";

		try {
			statement = connection.prepareStatement(SQL);
			statement.setInt(1, task.getId());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void assignId(Task task) {
		final String SQL = "SELECT MAX(id) AS id FROM tasks";

		try {
			statement = connection.prepareStatement(SQL);
			resultSet = statement.executeQuery();
			task.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}

			if (resultSet != null && !statement.isClosed()) {
				statement.close();
			}

			if (resultSet != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
