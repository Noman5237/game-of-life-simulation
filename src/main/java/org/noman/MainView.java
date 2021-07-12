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
	
	public MainView() {
		this.setOnKeyPressed(this::handleKeyPressed);
		
		ToolBar toolBar = new ToolBar(this);
		
		canvas = new Canvas(400, 400);
		canvas.setOnMousePressed(this::editCanvas);
		canvas.setOnMouseDragged(this::editCanvas);
		
		getChildren().addAll(toolBar, this.canvas);
		
		simulation = new Simulation(10, 10);
		transform = new Affine();
		transform.appendScale(400 / 10f, 400 / 10f);
		
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
		try {
			Point2D boardIndex = transform.inverseTransform(mouseEvent.getX(), mouseEvent.getY());
			simulation.setState((int) boardIndex.getX(), (int) boardIndex.getY(), drawMode);
		} catch (NonInvertibleTransformException e) {
			System.out.println("Mouse out of canvas!");
		}
		
		draw();
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
	}
}
