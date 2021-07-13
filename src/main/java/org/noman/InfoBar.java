package org.noman;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InfoBar extends HBox {
	
	final static String DRAW_MODE_FORMAT = "Draw Mode: %s";
	final static String CURSOR_POSITION_FORMAT = "Cursor Position: (%d, %d)";
	private Label drawModeIndicator;
	private Label cursorPosition;
	
	public InfoBar() {
		drawModeIndicator = new Label("Draw Mode: Drawing");
		cursorPosition = new Label("(0,0)");
		
		this.getChildren().addAll(drawModeIndicator, Spacer.Horizontal(), cursorPosition);
	}
	
	void setDrawMode(int mode) {
		String drawMode = "Erasing";
		if (mode == Simulation.ALIVE) {
			drawMode = "Drawing";
		}
		
		drawModeIndicator.setText(String.format(DRAW_MODE_FORMAT, drawMode));
	}
	
	void setCursorPosition(int x, int y) {
		cursorPosition.setText(String.format(CURSOR_POSITION_FORMAT, x, y));
	}
	
}
