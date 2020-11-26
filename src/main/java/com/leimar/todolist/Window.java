package com.leimar.todolist;

import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public abstract class Window {

	protected Stage stage;
	protected AnchorPane rootPane;
	protected static ResourceBundle labels;

	private final double NODES_MARGINS = 0.015;

	public Window() {
		labels = ResourceBundle.getBundle("com.leimar.todolist.languages.Labels", Locale.getDefault());
	}

	protected void positionOnTop(Node toPosition, double margin) {
		/* Position the node on top of the window */
		AnchorPane.setTopAnchor(toPosition, margin);
	}

	protected void positionOnLeft(Node toPosition, double margin) {
		/* Position the node on the left of the window */
		AnchorPane.setLeftAnchor(toPosition, margin);
	}

	protected void positionOnBottom(Node toPosition, double margin) {
		/* Position the node on bottom of the window */
		AnchorPane.setBottomAnchor(toPosition, margin);
	}

	protected void positionOnRight(Node toPosition, double margin) {
		/* Position the node on the right of the window */
		AnchorPane.setRightAnchor(toPosition, margin);
	}

	protected void positionOnTopOf(Node relative, Node toPosition, double margin) {
		/* position toPosition Node on top of relative Node */
		double relativeTopAnchor = getTopAnchor(relative);
		double bottomAnchor = relativeTopAnchor - margin;
		AnchorPane.setBottomAnchor(toPosition, bottomAnchor);
	}

	protected void positionOnLeftOf(Node relative, Node toPosition, double margin) {
		/* position toPosition Node on the left of relative Node */
		double relativeLeftAnchor = getLeftAnchor(relative);
		double rightAnchor = relativeLeftAnchor - margin;
		AnchorPane.setRightAnchor(toPosition, rightAnchor);
	}

	protected void positionBelowOf(Node relative, Node toPosition, double margin) {
		/* position toPosition Node below of relative Node */
		double relativeBottomAnchor = getBottomAnchor(relative);
		double topAnchor = relativeBottomAnchor + margin;
		AnchorPane.setTopAnchor(toPosition, topAnchor);
	}

	protected void positionOnRightOf(Node relative, Node toPosition, double margin) {
		/* position toPosition Node on the right of relative Node */
		double relativeRightAnchor = getRightAnchor(relative);
		double leftAnchor = relativeRightAnchor + margin;
		AnchorPane.setLeftAnchor(toPosition, leftAnchor);
	}

	protected double getTopAnchor(Node node) {
		/* Return the top anchor of the node */
		Double topAnchor = AnchorPane.getTopAnchor(node);

		if (topAnchor == null) {
			Double bottomAnchor = AnchorPane.getBottomAnchor(node);

			if (bottomAnchor == null) {
				bottomAnchor = 0.0;
			}

			topAnchor = bottomAnchor - ((Region) node).getHeight();
		}

		return topAnchor;
	}

	protected double getLeftAnchor(Node node) {
		/* Return the left anchor of the node */
		Double leftAnchor = AnchorPane.getLeftAnchor(node);

		if (leftAnchor == null) {
			Double rightAnchor = AnchorPane.getRightAnchor(node);

			if (rightAnchor == null) {
				rightAnchor = 0.0;
			}

			leftAnchor = rightAnchor - ((Region) node).getWidth();
		}

		return leftAnchor;
	}

	protected double getBottomAnchor(Node node) {
		/* Return the bottom anchor of the node */
		Double bottomAnchor = AnchorPane.getBottomAnchor(node);

		if (bottomAnchor == null) {
			Double topAnchor = AnchorPane.getTopAnchor(node);

			if (topAnchor == null) {
				topAnchor = 0.0;
			}

			bottomAnchor = topAnchor + ((Region) node).getHeight();
		}

		return bottomAnchor;
	}

	protected double getRightAnchor(Node node) {
		/* Return the right anchor of the node */
		Double rightAncor = AnchorPane.getRightAnchor(node);

		if (rightAncor == null) {
			Double leftAnchor = AnchorPane.getLeftAnchor(node);

			if (leftAnchor == null) {
				leftAnchor = 0.0;
			}

			rightAncor = leftAnchor + ((Region) node).getWidth();
		}

		return rightAncor;
	}

	protected double getScreenWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	protected double getScreenHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}

	protected double getSceneWidth() {
		return rootPane.getScene().getWidth();
	}

	protected double getSceneHeight() {
		return rootPane.getScene().getHeight();
	}

	protected double getHorizontalMargin() {
		Scene scene = rootPane.getScene();
		return scene.getWidth() * NODES_MARGINS;
	}

	protected double getVerticalMargin() {
		Scene scene = rootPane.getScene();
		return scene.getHeight() * NODES_MARGINS;
	}
}
