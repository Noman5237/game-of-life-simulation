package org.noman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {
	
	private Timeline timeline;
	private MainView mainView;
	
	public Simulator(MainView mainView) {
		this.mainView = mainView;
		timeline = new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
		timeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	private void doStep(ActionEvent actionEvent) {
		mainView.simulateStep(actionEvent);
	}
	
	public void start() {
		timeline.play();
	}
	
	public void stop() {
		timeline.stop();
	}
}
