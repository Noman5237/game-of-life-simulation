package org.noman.gol;

public class Simulation {
	
	static final int DEAD = 0;
	static final int ALIVE = 1;
	
	int width;
	int height;
	int[][] board;
	
	public Simulation(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.board = new int[height][width];
	}
	
	public static Simulation copy(Simulation simulation) {
		Simulation copy = new Simulation(simulation.width, simulation.height);
		for (int y = 0; y < copy.height; y++) {
			for (int x = 0; x < copy.height; x++) {
				copy.setState(x, y, simulation.getState(x, y));
			}
		}
		
		return copy;
	}
	
	public void printBoard() {
		System.out.println("---");
		for (int y = 0; y < height; y++) {
			String line = "";
			for (int x = 0; x < width; x++) {
				if (board[y][x] == DEAD) {
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
		setState(x, y, ALIVE);
	}
	
	public void setDead(int x, int y) {
		setState(x, y, DEAD);
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
				if (getState(x, y) == ALIVE) {
					if (aliveNeighbourCount < 2) {
						newBoard[y][x] = DEAD;
					} else if (aliveNeighbourCount == 2 || aliveNeighbourCount == 3) {
						newBoard[y][x] = ALIVE;
					} else if (aliveNeighbourCount > 3) {
						newBoard[y][x] = DEAD;
					}
				} else {
					if (aliveNeighbourCount == 3) {
						newBoard[y][x] = ALIVE;
					}
				}
			}
		}
		board = newBoard;
	}
	
	public void setState(int x, int y, int state) {
		if (isNotValidCoordinate(x, y)) return;
		
		board[y][x] = state;
		
	}
	
	private boolean isNotValidCoordinate(int x, int y) {
		if (x < 0 || x >= width) {
			return true;
		}
		return y < 0 || y >= height;
	}
}
