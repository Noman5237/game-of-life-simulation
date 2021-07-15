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
	private int drawMode = Simulation.ALIVE;
	private InfoBar infoBar;
	
	public static final int EDITING = 0;
	public static final int SIMULATING = 1;
	
	private Simulation editingSimulation;
	private int applicationState = EDITING;
	
	private Simulator simulator;
	
	public MainView() {
		this.setOnKeyPressed(this::handleKeyPressed);
		
		ToolBar toolBar = new ToolBar(this);
		infoBar = new InfoBar();
		
		canvas = new Canvas(400, 400);
		canvas.setOnMousePressed(this::editCanvas);
		canvas.setOnMouseDragged(this::editCanvas);
		canvas.setOnMouseMoved(this::handleMouseMoved);
		
		getChildren().addAll(toolBar, this.canvas, Spacer.Vertical(), infoBar);
		
		editingSimulation = new Simulation(10, 10);
		simulation = Simulation.copy(editingSimulation);
		transform = new Affine();
		transform.appendScale(400 / 10f, 400 / 10f);
		
		simulator = new Simulator(this);
	}
	
	private void handleMouseMoved(MouseEvent mouseEvent) {
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		infoBar.setCursorPosition(((int) boardIndex.getX()), ((int) boardIndex.getY()));
	}
	
	private void handleKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.D) {
			setDrawMode(Simulation.ALIVE);
		} else if (keyCode == KeyCode.E) {
			setDrawMode(Simulation.DEAD);
		} else if (keyCode == KeyCode.S) {
			simulateStep(new ActionEvent(keyEvent, this));
		} else if (keyCode == KeyCode.R) {
			resetSimulation(new ActionEvent(keyEvent, this));
		}
	}
	
	private void editCanvas(MouseEvent mouseEvent) {
		handleMouseMoved(mouseEvent);
		if (applicationState == SIMULATING) {
			return;
		}
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		editingSimulation.setState((int) boardIndex.getX(), (int) boardIndex.getY(), drawMode);
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
		if (applicationState == EDITING) {
			drawSimulation(editingSimulation);
		} else {
			drawSimulation(simulation);
		}
	}
	
	private void drawSimulation(Simulation simulationToDraw) {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.setTransform(transform);
		
		// background
		ctx.setFill(Color.LIGHTGRAY);
		ctx.fillRect(0, 0, 400, 400);
		
		// alive cells
		ctx.setFill(Color.BLACK);
		for (int y = 0; y < simulationToDraw.height; y++) {
			for (int x = 0; x < simulationToDraw.width; x++) {
				if (simulationToDraw.getState(x, y) == Simulation.ALIVE) {
					ctx.fillRect(x, y, 1, 1);
				}
			}
		}
		
		// grid lines
		ctx.setStroke(Color.GRAY);
		ctx.setLineWidth(0.05);
		for (int y = 0; y <= simulationToDraw.height; y++) {
			ctx.strokeLine(0, y, simulationToDraw.width, y);
		}
		for (int x = 0; x <= simulationToDraw.width; x++) {
			ctx.strokeLine(x, 0, x, simulationToDraw.height);
		}
	}
	
	public void setApplicationState(int applicationState) {
		if (this.applicationState == EDITING && applicationState == SIMULATING) {
			simulation = Simulation.copy(editingSimulation);
		}
		this.applicationState = applicationState;
	}
	
	public void simulateStep(ActionEvent actionEvent) {
		setApplicationState(SIMULATING);
		simulation.step();
		draw();
	}
	
	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
		infoBar.setDrawMode(drawMode);
	}
	
	public void resetSimulation(ActionEvent actionEvent) {
		setApplicationState(MainView.EDITING);
		stopContinuousSimulation();
		draw();
	}
	
	
	public void startContinuousSimulation() {
		setApplicationState(SIMULATING);
		simulator.start();
	}
	
	public void stopContinuousSimulation() {
		simulator.stop();
	}
}
