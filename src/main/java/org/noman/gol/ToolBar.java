package org.noman.gol;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ToolBar extends javafx.scene.control.ToolBar {
	
	private final MainView mainView;
	private final Simulator simulator;
	
	public ToolBar(MainView mainView) {
		this.mainView = mainView;
		this.simulator = new Simulator(this.mainView);
		
		Button draw = new Button("Draw");
		Button erase = new Button("Erase");
		Button step = new Button("Step");
		Button reset = new Button("Reset");
		Button start = new Button("Start");
		Button stop = new Button("Stop");
		
		this.getItems().addAll(draw, erase, reset, step, start, stop);
		
		draw.setOnAction(this::handleDraw);
		erase.setOnAction(this::handleErase);
		step.setOnAction(this::handleStep);
		reset.setOnAction(this::handleReset);
		start.setOnAction(this::handleStart);
		stop.setOnAction(this::handleStop);
		
	}
	
	public void startContinuousSimulation() {
		this.mainView.setApplicationState(MainView.SIMULATING);
		simulator.start();
	}
	
	public void stopContinuousSimulation() {
		simulator.stop();
	}
	
	private void handleStop(ActionEvent actionEvent) {
		stopContinuousSimulation();
	}
	
	private void handleStart(ActionEvent actionEvent) {
		startContinuousSimulation();
	}
	
	public void resetSimulation() {
		this.mainView.setApplicationState(MainView.EDITING);
		this.stopContinuousSimulation();
		this.mainView.draw();
	}
	
	private void handleReset(ActionEvent actionEvent) {
		this.resetSimulation();
	}
	
	private void handleStep(ActionEvent actionEvent) {
		mainView.simulateStep();
	}
	
	private void handleErase(ActionEvent actionEvent) {
		mainView.setDrawMode(CellState.DEAD);
	}
	
	private void handleDraw(ActionEvent actionEvent) {
		mainView.setDrawMode(CellState.ALIVE);
	}
}
