package org.noman.gol.rule;

import org.noman.gol.CellState;

public interface Rule {
	
	CellState getNextState(int x, int y);
}
