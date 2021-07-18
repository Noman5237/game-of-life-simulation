package org.noman.gol.model.board;

import org.noman.gol.CellState;

public class StandardBoundedBoard implements Board {
	
	private final int width;
	private final int height;
	private final CellState[][] board;
	
	public StandardBoundedBoard(int width, int height) {
		this.width = width;
		this.height = height;
		this.board = new CellState[height][width];
		
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				this.setCellState(x, y, CellState.DEAD);
			}
		}
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}
	
	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public CellState getCellState(int x, int y) {
		if (isNotValidCoordinate(x, y)) return CellState.DEAD;
		return board[y][x];
	}
	
	@Override
	public void setCellState(int x, int y, CellState cellState) {
		if (isNotValidCoordinate(x, y)) return;
		board[y][x] = cellState;
	}
	
	@Override
	public StandardBoundedBoard copy() {
		StandardBoundedBoard copy = new StandardBoundedBoard(this.width, this.height);
		for (int y = 0; y < copy.height; y++) {
			for (int x = 0; x < copy.width; x++) {
				copy.setCellState(x, y, this.getCellState(x, y));
			}
		}
		
		return copy;
	}
	
	private boolean isNotValidCoordinate(int x, int y) {
		if (x < 0 || x >= width) {
			return true;
		}
		return y < 0 || y >= height;
	}
	
}
