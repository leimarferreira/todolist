package com.leimar.todolist;

import java.util.List;

import com.leimar.todolist.database.TaskDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TaskListWindow extends Window {

	/* tasks choicebox indexes */
	private final static int SHOW_UNDONE = 0;
	private final static int SHOW_DONE = 1;
	private final static int SHOW_ALL = 2;

	/* Rate of the screen occupied by the width of window */
	private static final double WIDTH_RATE = 0.5;
	/* Rate of the screen occupied by the height of the window */
	private static final double HEIGHT_RATE = 0.6;

	/* nodes */
	private Button addButton;
	private Button editButton;
	private Button toogleDoneButton;
	private Button removeButton;
	private static ChoiceBox<String> tasksChoiceBox;
	private static ListView<Task> taskList;

	public TaskListWindow() {
		stage = new Stage();

		/* initialize nodes */
		addButton = new Button(labels.getString("addButton"));
		editButton = new Button(labels.getString("editButton"));
		editButton.setVisible(false);
		toogleDoneButton = new Button(labels.getString("toogleDoneUndone"));
		toogleDoneButton.setVisible(false);
		removeButton = new Button(labels.getString("removeButton"));
		removeButton.setVisible(false);

		tasksChoiceBox = new ChoiceBox<>();
		tasksChoiceBox.getItems().add(SHOW_UNDONE, labels.getString("choiceBoxUndone"));
		tasksChoiceBox.getItems().add(SHOW_DONE, labels.getString("choiceBoxDone"));
		tasksChoiceBox.getItems().add(SHOW_ALL, labels.getString("choiceBoxAll"));
		tasksChoiceBox.getSelectionModel().select(SHOW_UNDONE);

		taskList = new ListView<>();
		TaskDAO dao = new TaskDAO();
		List<Task> tasks = dao.getUndoneTasks(); // get the tasks
		dao.close();

		if (tasks != null) {
			taskList.setItems(FXCollections.observableList(tasks));
		}

		rootPane = new AnchorPane(addButton, editButton, toogleDoneButton, removeButton, tasksChoiceBox, taskList);
	}

	public void setNodesSize() {
		/* calculate the size of the window and margins */
		double sceneWidth = getSceneWidth();
		double sceneHeight = getSceneHeight();
		double horizontalMargin = getHorizontalMargin();
		double verticalMargin = getVerticalMargin();

		/* set the size of the nodes */
		taskList.setPrefWidth(sceneWidth - 2 * horizontalMargin);
		taskList.setPrefHeight(sceneHeight - addButton.getHeight() - (3 * verticalMargin));
	}

	public void setNodesPosition() {
		/* calculate the size of the window and margins */
		double horizontalMargin = getHorizontalMargin();
		double verticalMargin = getVerticalMargin();

		/* set the position of each node */
		positionOnTop(addButton, verticalMargin);
		positionOnLeft(addButton, horizontalMargin);
		positionOnTop(editButton, verticalMargin);
		positionOnRightOf(addButton, editButton, horizontalMargin);
		positionOnTop(toogleDoneButton, verticalMargin);
		positionOnRightOf(editButton, toogleDoneButton, horizontalMargin);
		positionOnTop(removeButton, verticalMargin);
		positionOnRightOf(toogleDoneButton, removeButton, horizontalMargin);
		positionOnTop(tasksChoiceBox, verticalMargin);
		positionOnRight(tasksChoiceBox, horizontalMargin);
		positionBelowOf(addButton, taskList, verticalMargin);
		positionOnLeft(taskList, horizontalMargin);
	}

	public void setEvents() {
		/* set the action when add button is pressed */
		addButton.setOnAction(event -> {
			Stage stage = (Stage) rootPane.getScene().getWindow();
			EditTaskWindow addTask = new EditTaskWindow(stage);
			addTask.start();
		});

		/* set the action when edit button is pressed */
		editButton.setOnAction(event -> {
			Task task = taskList.getSelectionModel().getSelectedItem(); // get the task to edit
			Stage stage = (Stage) rootPane.getScene().getWindow(); // get the stage
			EditTaskWindow editTask = new EditTaskWindow(stage, task); // create a new window for editing the task
			editTask.start();
			changeButtonsVisibility();
		});

		toogleDoneButton.setOnAction(event -> {
			Task task = taskList.getSelectionModel().getSelectedItem(); // get the task
			task.toogleDone();

			TaskDAO dao = new TaskDAO();
			dao.updateTask(task); // update database
			dao.close();

			int selected = tasksChoiceBox.getSelectionModel().getSelectedIndex();

			if (selected != SHOW_ALL) {
				taskList.getItems().remove(task);
			}

			changeButtonsVisibility(); // hide unused buttons
		});

		removeButton.setOnAction(event -> {
			Task task = taskList.getSelectionModel().getSelectedItem(); // get the task to remove
			taskList.getItems().remove(task); // remove the selected task
			TaskDAO dao = new TaskDAO();
			dao.removeTask(task); // remove the task from the database
			dao.close();
			changeButtonsVisibility(); // hide unused buttons
		});

		tasksChoiceBox.setOnAction(event -> {
			int selected = tasksChoiceBox.getSelectionModel().getSelectedIndex(); // get the selected option index
			ObservableList<Task> tasks = null;
			TaskDAO dao = new TaskDAO();

			if (selected == SHOW_UNDONE) {
				tasks = FXCollections.observableList(dao.getUndoneTasks());
			} else if (selected == SHOW_DONE) {
				tasks = FXCollections.observableList(dao.getDoneTasks());
			} else {
				tasks = FXCollections.observableList(dao.getAllTasks());
			}

			taskList.setItems(tasks);
			dao.close();
			changeButtonsVisibility();
		});

		taskList.setOnMouseClicked(event -> changeButtonsVisibility());
	}

	private void changeButtonsVisibility() {
		if (taskList.getSelectionModel().isEmpty()) {
			editButton.setVisible(false);
			toogleDoneButton.setVisible(false);
			removeButton.setVisible(false);
		} else {
			if (taskList.getSelectionModel().getSelectedItem().isDone()) {
				toogleDoneButton.setText(labels.getString("toogleDoneDone"));
			} else {
				toogleDoneButton.setText(labels.getString("toogleDoneUndone"));
			}

			positionOnRightOf(toogleDoneButton, removeButton, getHorizontalMargin());
			editButton.setVisible(true);
			toogleDoneButton.setVisible(true);
			removeButton.setVisible(true);
		}
	}

	@SuppressWarnings("exports")
	public static void addTask(Task task) {
		int selected = tasksChoiceBox.getSelectionModel().getSelectedIndex();
		if (selected != SHOW_DONE) {
			taskList.getItems().add(task);
		}
	}

	public static void refresh() {
		taskList.refresh();
	}

	public void start() {
		double sceneWidth = getScreenWidth() * WIDTH_RATE; // calculate the width of the window
		double sceneHeight = getScreenHeight() * HEIGHT_RATE; // calculate the height of the window

		Scene scene = new Scene(rootPane, sceneWidth, sceneHeight);

		stage.setScene(scene);
		stage.setTitle(labels.getString("taskListWindowTitle"));
		stage.setResizable(false);
		stage.show();

		setNodesSize(); // set the size of the nodes in relation to the window
		setNodesPosition(); // set the position of the nodes
		setEvents();
	}
}
