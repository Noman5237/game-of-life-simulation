package org.noman;

import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {
	
	private Canvas canvas;
	private Simulation simulation;
	private Affine transform;
	private int drawMode = 1;
	private InfoBar infoBar;
	
	public MainView() {
		this.setOnKeyPressed(this::handleKeyPressed);
		
		ToolBar toolBar = new ToolBar(this);
		infoBar = new InfoBar();
		
		canvas = new Canvas(400, 400);
		canvas.setOnMousePressed(this::editCanvas);
		canvas.setOnMouseDragged(this::editCanvas);
		canvas.setOnMouseMoved(this::handleMouseMoved);
		
		getChildren().addAll(toolBar, this.canvas, Spacer.Vertical(), infoBar);
		
		simulation = new Simulation(10, 10);
		transform = new Affine();
		transform.appendScale(400 / 10f, 400 / 10f);
		
	}
	
	private void handleMouseMoved(MouseEvent mouseEvent) {
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		infoBar.setCursorPosition(((int) boardIndex.getX()), ((int) boardIndex.getY()));
	}
	
	private void handleKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.D) {
			drawMode = 1;
		} else if (keyCode == KeyCode.E) {
			drawMode = 0;
		} else if (keyCode == KeyCode.S) {
			simulateStep(new ActionEvent(keyEvent, this));
		}
	}
	
	private void editCanvas(MouseEvent mouseEvent) {
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		simulation.setState((int) boardIndex.getX(), (int) boardIndex.getY(), drawMode);
		handleMouseMoved(mouseEvent);
		draw();
	}
	
	private Point2D getSimulationCoordinates(MouseEvent mouseEvent) {
		try {
			return transform.inverseTransform(mouseEvent.getX(), mouseEvent.getY());
		} catch (NonInvertibleTransformException e) {
			throw new RuntimeException("Simulation coordinated invertible");
		}
	}
	
	public void draw() {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.setTransform(transform);
		
		// background
		ctx.setFill(Color.LIGHTGRAY);
		ctx.fillRect(0, 0, 400, 400);
		
		// alive cells
		ctx.setFill(Color.BLACK);
		for (int y = 0; y < simulation.height; y++) {
			for (int x = 0; x < simulation.width; x++) {
				if (simulation.getState(x, y) == Simulation.ALIVE) {
					ctx.fillRect(x, y, 1, 1);
				}
			}
		}
		
		// grid lines
		ctx.setStroke(Color.GRAY);
		ctx.setLineWidth(0.05);
		for (int y = 0; y <= simulation.height; y++) {
			ctx.strokeLine(0, y, simulation.width, y);
		}
		for (int x = 0; x <= simulation.width; x++) {
			ctx.strokeLine(x, 0, x, simulation.height);
		}
		
	}
	
	public void simulateStep(ActionEvent actionEvent) {
		simulation.step();
		draw();
	}
	
	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
		infoBar.setDrawMode(drawMode);
	}
}
