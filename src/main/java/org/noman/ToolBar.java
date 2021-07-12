package org.noman;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ToolBar extends javafx.scene.control.ToolBar {
	
	private MainView mainView;
	
	public ToolBar(MainView mainView) {
		this.mainView = mainView;
		Button draw = new Button("Draw");
		Button erase = new Button("Erase");
		Button step = new Button("Step");
		
		this.getItems().addAll(draw, erase, step);
		
		draw.setOnAction(this::handleDraw);
		erase.setOnAction(this::handleErase);
		step.setOnAction(this::handleStep);
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
