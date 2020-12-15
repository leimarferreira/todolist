package com.leimar.todolist.languages;

import java.util.ListResourceBundle;

public class Labels extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
			{ "taskListWindowTitle", "Task List" },
			{ "editTaskWindowTitleAdd", "Add Task" },
			{ "editTaskWindowTitleEdit", "Edit Task" },
			{ "addButton", "Add" },
			{ "editButton", "Edit" },
			{ "toggleDoneUndone", "Mark as done" },
			{ "toggleDoneDone", "Mark as undone" },
			{ "removeButton", "Remove" },
			{ "choiceBoxUndone", "Show undone tasks" },
			{ "choiceBoxDone", "Show done tasks" },
			{ "choiceBoxAll", "Show all tasks" },
			{ "nameLabel", "Name" },
			{ "descriptionLabel", "Description" },
			{ "startDateLabel", "Start date" },
			{ "endDateLabel", "End date" },
			{ "cancelButton", "Cancel" },
			{ "saveButton", "Save" },
			{ "taskNameErrorTitle", "Error" },
			{ "taskNameErrorHeader", "Name is empty." },
			{ "taskNameErrorContext", "Name can't be empty." }
		};
	}

}
