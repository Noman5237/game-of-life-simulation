package org.noman.gol;

import org.noman.gol.model.board.Board;
import org.noman.gol.model.rule.Rule;

public class Simulation {
	
	private Board board;
	private final Rule rule;
	
	public Simulation(Board board, Rule rule) {
		this.board = board;
		this.rule = rule;
	}
	
	public void step() {
		Board nextState = this.board.copy();
		for (int y = 0; y < this.board.getHeight(); y++) {
			for (int x = 0; x < this.board.getWidth(); x++) {
				CellState newState = this.rule.getNextState(x, y, this.board);
				nextState.setCellState(x, y, newState);
			}
		}
		this.board = nextState;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
