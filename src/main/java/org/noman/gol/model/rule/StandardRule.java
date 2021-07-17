package org.noman.gol.model.rule;

import org.noman.gol.CellState;
import org.noman.gol.model.board.Board;

public class StandardRule implements Rule {
	
	public int countAliveNeighbours(int x, int y, Board board) {
		int count = 0;
		for (int yNeighbour = y - 1; yNeighbour <= y + 1; yNeighbour++) {
			for (int xNeighbour = x - 1; xNeighbour <= x + 1; xNeighbour++) {
				count += getCellCount(xNeighbour, yNeighbour, board);
			}
		}
		// because we have counted the cell [x][y] too if its alive
		count -= getCellCount(x, y, board);
		
		return count;
	}
	
	private int getCellCount(int x, int y, Board board) {
		return board.getCellState(x, y) == CellState.ALIVE ? 1 : 0;
	}
	
	@Override
	public CellState getNextState(int x, int y, Board board) {
		int aliveNeighbourCount = countAliveNeighbours(x, y, board);
		if (board.getCellState(x, y) == CellState.ALIVE) {
			if (aliveNeighbourCount < 2) {
				return CellState.DEAD;
			} else if (aliveNeighbourCount == 2 || aliveNeighbourCount == 3) {
				return CellState.ALIVE;
			} else {
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
