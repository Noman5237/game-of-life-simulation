package org.noman.gol;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ToolBar extends javafx.scene.control.ToolBar {
	
	private MainView mainView;
	
	public ToolBar(MainView mainView) {
		this.mainView = mainView;
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
	
	private void handleStop(ActionEvent actionEvent) {
		mainView.stopContinuousSimulation();
	}
	
	private void handleStart(ActionEvent actionEvent) {
		mainView.startContinuousSimulation();
	}
	
	private void handleReset(ActionEvent actionEvent) {
		mainView.resetSimulation(actionEvent);
	}
	
	private void handleStep(ActionEvent actionEvent) {
		mainView.simulateStep(actionEvent);
	}
	
	private void handleErase(ActionEvent actionEvent) {
		mainView.setDrawMode(Simulation.DEAD);
	}
	
	private void handleDraw(ActionEvent actionEvent) {
		mainView.setDrawMode(Simulation.ALIVE);
	}
}
