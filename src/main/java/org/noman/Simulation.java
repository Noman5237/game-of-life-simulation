package org.noman;

public class Simulation {
	
	int width;
	int height;
	int[][] board;
	
	public Simulation(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.board = new int[height][width];
	}
	
	public void printBoard() {
		System.out.println("---");
		for (int y = 0; y < height; y++) {
			String line = "";
			for (int x = 0; x < width; x++) {
				if (board[y][x] == 0) {
					line += ".";
				} else {
					line += "*";
				}
			}
			line += "|";
			System.out.println(line);
		}
		System.out.println("---");
	}
	
	public void setAlive(int x, int y) {
		board[y][x] = 1;
	}
	
	public void setDead(int x, int y) {
		board[y][x] = 0;
	}
	
	public int getState(int x, int y) {
		if (x < 0 || x >= width) {
			return 0;
		}
		if (y < 0 || y >= height) {
			return 0;
		}
		
		return board[y][x];
	}
	
	public int countAliveNeighbours(int x, int y) {
		int count = 0;
		
		for (int yNeighbour = y - 1; yNeighbour <= y + 1; yNeighbour++) {
			for (int xNeighbour = x - 1; xNeighbour <= x + 1; xNeighbour++) {
				count += getState(xNeighbour, yNeighbour);
			}
		}
		// because we have counted the cell [x][y] too if its alive
		count -= board[y][x];
		
		return count;
	}
	
	public void step() {
		int[][] newBoard = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				int aliveNeighbourCount = countAliveNeighbours(x, y);
				if (getState(x, y) == 1) {
					if (aliveNeighbourCount < 2) {
						newBoard[y][x] = 0;
					} else if (aliveNeighbourCount == 2 || aliveNeighbourCount == 3) {
						newBoard[y][x] = 1;
					} else if (aliveNeighbourCount > 3) {
						newBoard[y][x] = 0;
					}
				} else {
					if (aliveNeighbourCount == 3) {
						newBoard[y][x] = 1;
					}
				}
			}
		}
		board = newBoard;
	}
	
	public static void main(String[] args) {
		Simulation simulation = new Simulation(8, 5);
		simulation.setAlive(2, 2);
		simulation.setAlive(3, 2);
		simulation.setAlive(4, 2);
		simulation.printBoard();
		
		for (int i = 0; i < 5; i++) {
			simulation.step();
			simulation.printBoard();
		}
	}
	
	
}
