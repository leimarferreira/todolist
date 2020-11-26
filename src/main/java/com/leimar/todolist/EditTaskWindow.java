package com.leimar.todolist;

import com.leimar.todolist.database.TaskDAO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditTaskWindow extends Window {

	private Task task;

	private Label nameLabel;
	private Label nameCharCount;
	private TextField nameField;
	private Label descriptionLabel;
	private Label descriptionCharCount;
	private TextArea descriptionArea;
	private Label startDateLabel;
	private DatePicker startDatePicker;
	private Label endDateLabel;
	private DatePicker endDatePicker;
	private Button cancelButton;
	private Button saveButton;

	private final int NAME_CHAR_LIMIT = 50;
	private final int DESCRIPTION_CHAR_LIMIT = 150;

	/* Rate of the screen occupied by the width of window */
	private final double WIDTH_RATIO = 0.32;
	/* Rate of the screen occupied by the height of the window */
	private final double HEIGHT_RATIO = 0.31;

	@SuppressWarnings("rawtypes")
	private EventHandler saveButtonAction;

	@SuppressWarnings({ "exports", "unchecked" })
	public EditTaskWindow(Stage ownerStage) {
		stage = new Stage();
		stage.initOwner(ownerStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(labels.getString("editTaskWindowTitleAdd"));
		task = new Task();

		nameLabel = new Label(labels.getString("nameLabel"));
		nameField = new TextField();
		nameCharCount = new Label(String.format("%d/%d", nameField.getText().length(), NAME_CHAR_LIMIT));
		descriptionLabel = new Label(labels.getString("descriptionLabel"));
		descriptionArea = new TextArea();
		descriptionCharCount = new Label(
				String.format("%d/%d", descriptionArea.getText().length(), DESCRIPTION_CHAR_LIMIT));
		descriptionArea.setWrapText(true);
		startDateLabel = new Label(labels.getString("startDateLabel"));
		startDatePicker = new DatePicker();
		endDateLabel = new Label(labels.getString("endDateLabel"));
		endDatePicker = new DatePicker();
		cancelButton = new Button(labels.getString("cancelButton"));
		saveButton = new Button(labels.getString("saveButton"));

		saveButtonAction = event -> {
			if (nameField.getText() != null) {
				task.setName(nameField.getText());
			}

			if (descriptionArea.getText() != null) {
				task.setDescription(descriptionArea.getText());
			}

			if (startDatePicker.getValue() != null) {
				task.setStartDate(startDatePicker.getValue());
			}

			if (endDatePicker.getValue() != null) {
				task.setEndDate(endDatePicker.getValue());
			}

			if (task.getName() != null) {
				TaskDAO dao = new TaskDAO();
				dao.insertTask(task);
				dao.close();
				TaskListWindow.addTask(task);
				stage.close();
			} else {
				showSetNameError();
			}
		};

		saveButton.setOnAction(saveButtonAction);

		rootPane = new AnchorPane(nameLabel, nameCharCount, nameField, descriptionLabel, descriptionCharCount,
				descriptionArea, startDateLabel, startDatePicker, endDateLabel, endDatePicker, cancelButton,
				saveButton);

	}

	@SuppressWarnings({ "exports", "unchecked" })
	public EditTaskWindow(Stage ownerStage, Task task) {
		this(ownerStage);
		this.task = task;
		stage.setTitle("Edit Task");

		if (task.getName() != null) {
			nameField.setText(task.getName());
		}

		if (task.getDescription() != null) {
			descriptionArea.setText(task.getDescription());
		}

		if (task.getStartDate() != null) {
			startDatePicker.setValue(task.getStartDate());
		}

		if (task.getEndDate() != null) {
			endDatePicker.setValue(task.getEndDate());
		}

		saveButtonAction = event -> {
			if (nameField.getText() != null) {
				task.setName(nameField.getText());
			}

			if (descriptionArea.getText() != null) {
				task.setDescription(descriptionArea.getText());
			}

			if (startDatePicker.getValue() != null) {
				task.setStartDate(startDatePicker.getValue());
			}

			if (endDatePicker.getValue() != null) {
				task.setEndDate(endDatePicker.getValue());
			}

			if (task.getName() != null) {
				TaskDAO dao = new TaskDAO();
				dao.updateTask(task);
				dao.close();
				TaskListWindow.refresh();
				stage.close();
			} else {
				showSetNameError();
			}
		};
		saveButton.setOnAction(saveButtonAction);
	}

	private void setNodesSize() {
		/* calculate the size of the window and margins */
		double horizontalMargin = getHorizontalMargin();
		/* calculate the size of the nodes */
		double textFieldsWidth = getSceneWidth() - 2 * horizontalMargin;
		double descriptionAreaHeight = 3 * nameField.getHeight();
		double datePickersWidth = (getSceneWidth() - 4 * horizontalMargin) / 2;

		/* set the size of the nodes */
		nameField.setPrefWidth(textFieldsWidth);
		nameField.setMinWidth(Region.USE_PREF_SIZE);
		nameField.setMaxWidth(Region.USE_PREF_SIZE);

		nameField.setPrefHeight(nameField.getHeight());
		nameField.setMinHeight(Region.USE_PREF_SIZE);
		nameField.setMaxHeight(Region.USE_PREF_SIZE);

		descriptionArea.setPrefWidth(textFieldsWidth);
		descriptionArea.setMaxWidth(Region.USE_PREF_SIZE);
		descriptionArea.setMinWidth(Region.USE_PREF_SIZE);

		descriptionArea.setPrefHeight(descriptionAreaHeight);
		descriptionArea.setMaxHeight(Region.USE_PREF_SIZE);
		descriptionArea.setMinHeight(Region.USE_PREF_SIZE);

		descriptionArea.resize(descriptionArea.getPrefWidth(), descriptionArea.getPrefHeight());

		startDatePicker.setPrefWidth(datePickersWidth);
		startDatePicker.setMinWidth(datePickersWidth);
		startDatePicker.setMaxWidth(datePickersWidth);

		startDatePicker.resize(startDatePicker.getPrefWidth(), startDatePicker.getHeight());

		endDatePicker.setPrefWidth(datePickersWidth);
		endDatePicker.setMinWidth(datePickersWidth);
		endDatePicker.setMaxWidth(datePickersWidth);

		endDatePicker.resize(endDatePicker.getWidth(), endDatePicker.getHeight());
	}

	private void setNodesPosition() {
		/* calculate the size of the margins */
		double verticalMargin = getVerticalMargin();
		double horizontalMargin = getHorizontalMargin();

		/* set the position of each node */
		positionOnTop(nameLabel, verticalMargin);
		positionOnLeft(nameLabel, horizontalMargin);
		positionOnTop(nameCharCount, verticalMargin);
		positionOnRight(nameCharCount, horizontalMargin);
		positionBelowOf(nameLabel, nameField, verticalMargin);
		positionOnLeft(nameField, horizontalMargin);
		positionBelowOf(nameField, descriptionLabel, verticalMargin);
		positionOnLeft(descriptionLabel, horizontalMargin);
		positionBelowOf(nameField, descriptionCharCount, verticalMargin);
		positionOnRight(descriptionCharCount, horizontalMargin);
		positionBelowOf(descriptionLabel, descriptionArea, verticalMargin);
		positionOnLeft(descriptionArea, horizontalMargin);
		positionBelowOf(descriptionArea, startDateLabel, verticalMargin);
		positionOnLeft(startDateLabel, horizontalMargin);
		positionBelowOf(startDateLabel, startDatePicker, verticalMargin);
		positionOnLeft(startDatePicker, horizontalMargin);
		positionBelowOf(descriptionArea, endDateLabel, verticalMargin);
		positionOnRightOf(startDatePicker, endDateLabel, 2 * horizontalMargin);
		positionBelowOf(endDateLabel, endDatePicker, verticalMargin);
		positionOnRight(endDatePicker, horizontalMargin);
		positionOnBottom(cancelButton, verticalMargin);
		positionOnLeft(cancelButton, horizontalMargin);
		positionOnBottom(saveButton, verticalMargin);
		positionOnRight(saveButton, horizontalMargin);
	}

	private void setEvents() {
		/* set cancelButton action */
		cancelButton.setOnAction(event -> stage.close());

		/* set the limit of characters of the nameField TextField */
		nameField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (nameField.getText().length() > NAME_CHAR_LIMIT) {
						nameField.setText(nameField.getText().substring(0, NAME_CHAR_LIMIT));
					}
				}

				/* update the name char counter */
				String text = String.format("%d/%d", nameField.getText().length(), NAME_CHAR_LIMIT);
				nameCharCount.setText(text);

				/* update the position of the label */
				double margin = getHorizontalMargin();
				positionOnRight(nameCharCount, margin);
			}

		});

		/*
		 * set the limit of character of descriptionArea and update description char
		 * count
		 */
		descriptionArea.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (descriptionArea.getText().length() > DESCRIPTION_CHAR_LIMIT) {
						descriptionArea.setText(descriptionArea.getText().substring(0, DESCRIPTION_CHAR_LIMIT));
					}
				}

				/* update the description char counter */
				String text = String.format("%d/%d", descriptionArea.getText().length(), DESCRIPTION_CHAR_LIMIT);
				descriptionCharCount.setText(text);

				/* update the position of the label */
				double margin = getHorizontalMargin();
				positionOnRight(descriptionCharCount, margin);
			}
		});
	}

	private void showSetNameError() {
		Alert errorSettingName = new Alert(AlertType.ERROR);
		errorSettingName.setTitle(labels.getString("taskNameErrorTitle"));
		errorSettingName.setHeaderText(labels.getString("taskNameErrorHeader"));
		errorSettingName.setContentText(labels.getString("taskNameErrorContext"));
		errorSettingName.show();
	}

	public void start() {
		double sceneWidht = getScreenWidth() * WIDTH_RATIO; // calulate the width of the scene
		double sceneHeight = getScreenHeight() * HEIGHT_RATIO; // calculate the height of the scene
		Scene scene = new Scene(rootPane, sceneWidht, sceneHeight);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		setNodesSize();
		setNodesPosition();
		setEvents();
	}
}
