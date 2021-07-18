package org.noman.gol.util;

import org.junit.jupiter.api.extension.Extension;
import org.noman.gol.CellState;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

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
	
	public static Stream<ArrayList<CellPositionNState>> getRandomPositionNState(int boardWidth, int boardHeight, int noOfArgs) {
		ArrayList<CellPositionNState> cellPositionNStates = new ArrayList<>();
		while (cellPositionNStates.size() != noOfArgs) {
			boolean unique = true;
			Random random = new Random();
			int x = random.nextInt(boardWidth);
			int y = random.nextInt(boardHeight);
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
	
	@Override
	public String toString() {
		return "CellPositionNState{" +
				"x=" + x +
				", y=" + y +
				", cellState=" + cellState +
				'}';
	}
}
