package com.leimar.todolist;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) {
		TaskListWindow mainWindow = new TaskListWindow();
		mainWindow.start();
	}

	public static void main(String[] args) {
		launch();
	}

}