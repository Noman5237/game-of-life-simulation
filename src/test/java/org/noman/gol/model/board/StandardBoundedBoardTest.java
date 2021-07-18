package org.noman.gol.model.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.noman.gol.CellState;
import org.noman.gol.util.CellPositionNState;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardBoundedBoardTest {
	
	private Board board;
	private static final int BOARD_WIDTH = 5;
	private static final int BOARD_HEIGHT = 3;
	private static final int MAX_DYNAMIC_ARGS = 10;
	
	@BeforeEach
	void setUp() {
		this.board = new StandardBoundedBoard(BOARD_WIDTH, BOARD_HEIGHT);
	}
	
	@Test
	@DisplayName ("get width test")
	void testGetWidth() {
		assertEquals(BOARD_WIDTH, board.getWidth());
		board = new StandardBoundedBoard(10, 5);
		assertEquals(10, board.getWidth());
	}
	
	@Test
	@DisplayName ("get width test")
	void testGetHeight() {
		assertEquals(BOARD_HEIGHT, board.getHeight());
		board = new StandardBoundedBoard(10, 5);
		assertEquals(5, board.getHeight());
	}
	
	@ParameterizedTest (name = "getter and setter testing with random values")
	@MethodSource ("getRandomPositionNState")
	@ExtendWith (CellPositionNState.class)
	void testSetNGetCellStateOfValidCells(ArrayList<CellPositionNState> cellPositionNStates) {
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			board.setCellState(cellPositionNState.getX(), cellPositionNState.getY(), cellPositionNState.getCellState());
		}
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			assertEquals(cellPositionNState.getCellState(), board.getCellState(cellPositionNState.getX(), cellPositionNState.getY()));
		}
	}
	
	@ParameterizedTest (name = "set invalid cells to {2} and assert states of invalid cells are DEAD")
	@CsvSource ({
			"10, 11, ALIVE",
			"-1, -2, ALIVE",
			"5, 3, DEAD",
			"-99, 100, ALIVE",
			"1, -2, ALIVE",
	})
	void testSetNGetCellStateOfInvalidCells(int x, int y, CellState cellState) {
		board.setCellState(x, y, cellState);
		assertEquals(CellState.DEAD, board.getCellState(x, y));
	}
	
	
	@ParameterizedTest (name = "copying board states correctly")
	@MethodSource ("getRandomPositionNState")
	@ExtendWith (CellPositionNState.class)
	void testCopyCorrectStates(ArrayList<CellPositionNState> cellPositionNStates) {
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			board.setCellState(cellPositionNState.getX(), cellPositionNState.getY(), cellPositionNState.getCellState());
		}
		
		Board copyBoard = board.copy();
		
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			assertEquals(cellPositionNState.getCellState(), copyBoard.getCellState(cellPositionNState.getX(), cellPositionNState.getY()));
		}
		
	}
	
	@ParameterizedTest (name = "deep copying board")
	@MethodSource ("getRandomPositionNState")
	@ExtendWith (CellPositionNState.class)
	void testDeepCopy(ArrayList<CellPositionNState> cellPositionNStates) {
		Board copyBoard = board.copy();
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			board.setCellState(cellPositionNState.getX(), cellPositionNState.getY(), cellPositionNState.getCellState());
		}
		for (CellPositionNState cellPositionNState : cellPositionNStates) {
			assertEquals(CellState.DEAD, copyBoard.getCellState(cellPositionNState.getX(), cellPositionNState.getY()));
		}
		
	}
	
	static Stream<ArrayList<CellPositionNState>> getRandomPositionNState() {
		ArrayList<CellPositionNState> cellPositionNStates = new ArrayList<>();
		while (cellPositionNStates.size() != MAX_DYNAMIC_ARGS) {
			boolean unique = true;
			Random random = new Random();
			int x = random.nextInt(BOARD_WIDTH);
			int y = random.nextInt(BOARD_HEIGHT);
			for (CellPositionNState cellPositionNState : cellPositionNStates) {
				if (cellPositionNState.getX() == x && cellPositionNState.getY() == y) {
					unique = false;
					break;
				}
			}
			if (unique) {
				CellState cellState = CellState.values()[random.nextInt(CellState.values().length)];
				cellPositionNStates.add(new CellPositionNState(x, y, cellState));
			}
		}
		
		return Stream.of(cellPositionNStates);
	}
}