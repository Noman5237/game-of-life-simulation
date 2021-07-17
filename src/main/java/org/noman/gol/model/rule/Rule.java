package org.noman.gol.model.rule;

import org.noman.gol.CellState;
import org.noman.gol.model.board.Board;

public interface Rule {
	
	CellState getNextState(int x, int y, Board board);
}
