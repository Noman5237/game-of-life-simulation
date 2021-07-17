package org.noman.gol;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Spacer {
	
	static Pane Horizontal() {
		Pane spacer = getPane();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}
	
	static Pane Vertical() {
		Pane spacer = getPane();
		VBox.setVgrow(spacer, Priority.ALWAYS);
		return spacer;
	}
	
	private static Pane getPane() {
		Pane spacer = new Pane();
		spacer.setMinSize(0, 0);
		spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return spacer;
	}
}
