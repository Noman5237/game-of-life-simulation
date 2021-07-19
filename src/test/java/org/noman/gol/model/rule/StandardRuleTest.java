package org.noman.gol.model.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.noman.gol.CellState;
import org.noman.gol.model.board.Board;
import org.noman.gol.model.board.StandardBoundedBoard;
import org.noman.gol.util.CellPositionNState;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardRuleTest {
	
	private static final int BOARD_WIDTH = 7;
	private static final int BOARD_HEIGHT = 5;
	
	private Board board;
	private Rule rule;
	private CellPositionNState testCell;
	
	@BeforeEach
	void setUp() {
		board = new StandardBoundedBoard(BOARD_WIDTH, BOARD_HEIGHT);
		rule = new StandardRule();
		testCell = new CellPositionNState(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, CellState.ALIVE);
		board.setCellState(testCell.getX(), testCell.getY(), testCell.getCellState());
	}
	
	@Test
	void testGetNextStateWithNoAliveNeighbours() {
		assertEquals(CellState.DEAD, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateWithOneAliveNeighbours() {
		board.setCellState(testCell.getX() - 1, testCell.getY(), CellState.ALIVE);
		
		assertEquals(CellState.DEAD, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateWithTwoAliveNeighbours() {
		board.setCellState(testCell.getX() - 1, testCell.getY(), CellState.ALIVE);
		board.setCellState(testCell.getX() - 1, testCell.getY() - 1, CellState.ALIVE);
		
		assertEquals(CellState.ALIVE, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateWithThreeAliveNeighbours() {
		board.setCellState(testCell.getX(), testCell.getY() + 1, CellState.ALIVE);
		board.setCellState(testCell.getX() - 1, testCell.getY() - 1, CellState.ALIVE);
		board.setCellState(testCell.getX() + 1, testCell.getY() - 1, CellState.ALIVE);
		
		assertEquals(CellState.ALIVE, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateWithFourAliveNeighbours() {
		board.setCellState(testCell.getX(), testCell.getY() + 1, CellState.ALIVE);
		board.setCellState(testCell.getX() - 1, testCell.getY() - 1, CellState.ALIVE);
		board.setCellState(testCell.getX() + 1, testCell.getY() - 1, CellState.ALIVE);
		board.setCellState(testCell.getX() - 1, testCell.getY(), CellState.ALIVE);
		
		assertEquals(CellState.DEAD, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateWithEightAliveNeighbours() {
		for (int yNeighbour = testCell.getY() - 1; yNeighbour <= testCell.getY() + 1; yNeighbour++) {
			for (int xNeighbour = testCell.getX() - 1; xNeighbour <= testCell.getX() + 1; xNeighbour++) {
				board.setCellState(xNeighbour, yNeighbour, CellState.ALIVE);
			}
		}
		
		assertEquals(CellState.DEAD, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	@Test
	void testGetNextStateReproductionWithThreeAliveNeighbours() {
		board.setCellState(testCell.getX(), testCell.getY(), CellState.DEAD);
		
		board.setCellState(testCell.getX(), testCell.getY() + 1, CellState.ALIVE);
		board.setCellState(testCell.getX() - 1, testCell.getY() - 1, CellState.ALIVE);
		board.setCellState(testCell.getX() + 1, testCell.getY() - 1, CellState.ALIVE);
		
		assertEquals(CellState.ALIVE, rule.getNextState(testCell.getX(), testCell.getY(), board));
	}
	
	
}