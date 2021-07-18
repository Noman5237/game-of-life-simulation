package org.noman.gol;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;
import org.noman.gol.model.board.Board;
import org.noman.gol.model.board.StandardBoundedBoard;
import org.noman.gol.model.rule.StandardRule;
import org.noman.gol.util.Spacer;

public class MainView extends VBox {
	
	private final Board board;
	private Simulation simulation;
	
	private final ToolBar toolBar;
	private final InfoBar infoBar;
	private final Canvas canvas;
	private final Affine transform;
	
	private CellState drawMode = CellState.ALIVE;
	public static final int EDITING = 0;
	public static final int SIMULATING = 1;
	private int applicationState = EDITING;
	
	public MainView() {
		this.board = new StandardBoundedBoard(10, 10);
		this.simulation = new Simulation(board, new StandardRule());
		this.toolBar = new ToolBar(this);
		this.infoBar = new InfoBar();
		this.canvas = new Canvas(400, 400);
		getChildren().addAll(toolBar, this.canvas, Spacer.Vertical(), infoBar);
		
		this.transform = new Affine();
		this.transform.appendScale(400 / 10f, 400 / 10f);
		
		this.setOnKeyPressed(this::handleKeyPressed);
		this.canvas.setOnMousePressed(this::editBoard);
		this.canvas.setOnMouseDragged(this::editBoard);
		this.canvas.setOnMouseMoved(this::handleMouseMoved);
	}
	
	public void draw() {
		if (applicationState == EDITING) {
			drawBoard(board);
		} else {
			drawBoard(simulation.getBoard());
		}
	}
	
	private void drawBoard(Board boardToDraw) {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.setTransform(transform);
		
		// background
		ctx.setFill(Color.LIGHTGRAY);
		ctx.fillRect(0, 0, 400, 400);
		
		// alive cells
		ctx.setFill(Color.BLACK);
		for (int y = 0; y < boardToDraw.getHeight(); y++) {
			for (int x = 0; x < boardToDraw.getWidth(); x++) {
				if (boardToDraw.getCellState(x, y) == CellState.ALIVE) {
					ctx.fillRect(x, y, 1, 1);
				}
			}
		}
		
		// grid lines
		ctx.setStroke(Color.GRAY);
		ctx.setLineWidth(0.05);
		for (int y = 0; y <= boardToDraw.getHeight(); y++) {
			ctx.strokeLine(0, y, boardToDraw.getWidth(), y);
		}
		for (int x = 0; x <= boardToDraw.getWidth(); x++) {
			ctx.strokeLine(x, 0, x, boardToDraw.getHeight());
		}
	}
	
	private void editBoard(MouseEvent mouseEvent) {
		handleMouseMoved(mouseEvent);
		if (applicationState == SIMULATING) {
			return;
		}
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		this.board.setCellState((int) boardIndex.getX(), (int) boardIndex.getY(), drawMode);
		draw();
	}
	
	public void simulateStep() {
		setApplicationState(SIMULATING);
		simulation.step();
		draw();
	}
	
	
	private void handleMouseMoved(MouseEvent mouseEvent) {
		Point2D boardIndex = getSimulationCoordinates(mouseEvent);
		infoBar.setCursorPosition(((int) boardIndex.getX()), ((int) boardIndex.getY()));
	}
	
	private void handleKeyPressed(KeyEvent keyEvent) {
		KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.D) {
			setDrawMode(CellState.ALIVE);
		} else if (keyCode == KeyCode.E) {
			setDrawMode(CellState.DEAD);
		} else if (keyCode == KeyCode.S) {
			simulateStep();
		} else if (keyCode == KeyCode.R) {
			toolBar.resetSimulation();
		}
	}
	
	private Point2D getSimulationCoordinates(MouseEvent mouseEvent) {
		try {
			return transform.inverseTransform(mouseEvent.getX(), mouseEvent.getY());
		} catch (NonInvertibleTransformException e) {
			throw new RuntimeException("Simulation coordinated invertible");
		}
	}
	
	public void setApplicationState(int applicationState) {
		if (this.applicationState == EDITING && applicationState == SIMULATING) {
			this.simulation = new Simulation(this.board.copy(), new StandardRule());
		}
		this.applicationState = applicationState;
	}
	
	public void setDrawMode(CellState drawMode) {
		this.drawMode = drawMode;
		infoBar.setDrawMode(drawMode);
	}
	
}
