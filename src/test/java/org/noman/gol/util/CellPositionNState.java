package org.noman.gol.util;

import org.junit.jupiter.api.extension.Extension;
import org.noman.gol.CellState;

public class CellPositionNState implements Extension {
	
	private int x;
	private int y;
	private CellState cellState;
	
	public CellPositionNState() {
	}
	
	public CellPositionNState(int x, int y, CellState cellState) {
		this.x = x;
		this.y = y;
		this.cellState = cellState;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public CellState getCellState() {
		return cellState;
	}
	
	@Override
	public String toString() {
		return "CellPositionNState{" +
				"x=" + x +
				", y=" + y +
				", cellState=" + cellState +
				'}';
	}
}
