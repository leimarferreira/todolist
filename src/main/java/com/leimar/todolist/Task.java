package com.leimar.todolist;

import java.time.LocalDate;

public class Task {

	private int id;
	private String name;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean isDone;

	public Task() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name != null && !name.isBlank()) {
			this.name = name;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null && !description.isBlank()) {
			this.description = description;
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public long getStartInEpochDay() {
		if (startDate != null) {
			return startDate.toEpochDay();
		}

		return -1l;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public void setStartDate(long epochDay) {
		if (epochDay != -1l) {
			this.startDate = LocalDate.ofEpochDay(epochDay);
		} else {
			this.startDate = null;
		}
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public long getEndDateInEpochDay() {
		if (endDate != null) {
			return endDate.toEpochDay();
		}

		return -1l;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setEndDate(long epochDay) {
		if (epochDay != -1l) {
			this.endDate = LocalDate.ofEpochDay(epochDay);
		} else {
			this.endDate = null;
		}
	}

	public boolean isDone() {
		return this.isDone;
	}

	public void toogleDone() {
		isDone = !isDone;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
