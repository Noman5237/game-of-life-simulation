package org.noman.gol;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.noman.gol.util.Spacer;

public class InfoBar extends HBox {
	
	final static String DRAW_MODE_FORMAT = "Draw Mode: %s";
	final static String CURSOR_POSITION_FORMAT = "Cursor Position: (%d, %d)";
	private final Label drawModeIndicator;
	private final Label cursorPosition;
	
	public InfoBar() {
		drawModeIndicator = new Label("Draw Mode: Drawing");
		cursorPosition = new Label("(0,0)");
		
		this.getChildren().addAll(drawModeIndicator, Spacer.Horizontal(), cursorPosition);
	}
	
	void setDrawMode(CellState mode) {
		String drawMode = "Erasing";
		if (mode == CellState.ALIVE) {
			drawMode = "Drawing";
		}
		
		drawModeIndicator.setText(String.format(DRAW_MODE_FORMAT, drawMode));
	}
	
	void setCursorPosition(int x, int y) {
		cursorPosition.setText(String.format(CURSOR_POSITION_FORMAT, x, y));
	}
	
}
