package org.noman.gol.rule;

import org.noman.gol.CellState;
import org.noman.gol.board.Board;

public class StandardRule implements Rule {
	
	private final Board board;
	
	public StandardRule(Board board) {
		this.board = board;
	}
	
	public int countAliveNeighbours(int x, int y) {
		int count = 0;
		for (int yNeighbour = y - 1; yNeighbour <= y + 1; yNeighbour++) {
			for (int xNeighbour = x - 1; xNeighbour <= x + 1; xNeighbour++) {
				count += getCellCount(xNeighbour, yNeighbour);
			}
		}
		// because we have counted the cell [x][y] too if its alive
		count -= getCellCount(x, y);
		
		return count;
	}
	
	private int getCellCount(int x, int y) {
		return this.board.getCellState(x, y) == CellState.ALIVE ? 1 : 0;
	}
	
	@Override
	public CellState getNextState(int x, int y) {
		int aliveNeighbourCount = countAliveNeighbours(x, y);
		if (this.board.getCellState(x, y) == CellState.ALIVE) {
			if (aliveNeighbourCount < 2) {
				return CellState.DEAD;
			} else if (aliveNeighbourCount == 2 || aliveNeighbourCount == 3) {
				return CellState.ALIVE;
			} else if (aliveNeighbourCount > 3) {
				return CellState.DEAD;
			}
		} else {
			if (aliveNeighbourCount == 3) {
				return CellState.ALIVE;
			}
		}
		
		return CellState.DEAD;
	}
}
