package org.noman.gol.model.board;

import org.noman.gol.CellState;

public interface Board {
	
	int getWidth();
	
	int getHeight();
	
	CellState getCellState(int x, int y);
	
	void setCellState(int x, int y, CellState cellState);
	
	Board copy();
}
