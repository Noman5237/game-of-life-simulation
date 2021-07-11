package org.noman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MainView extends VBox {
	
	private Button stepButton;
	private Canvas canvas;
	private Simulation simulation;
	private Affine transform;
	
	public MainView() {
		stepButton = new Button("step");
		stepButton.setOnAction(actionEvent -> {
			simulation.step();
			draw();
		});
		canvas = new Canvas(400, 400);
		
		getChildren().addAll(this.stepButton, this.canvas);
		
		simulation = new Simulation(10, 10);
		transform = new Affine();
		transform.appendScale(400 / 10f, 400 / 10f);
		
		simulation.setAlive(2, 2);
		simulation.setAlive(3, 2);
		simulation.setAlive(4, 2);
		
		simulation.setAlive(5, 5);
		simulation.setAlive(5, 6);
		simulation.setAlive(6, 5);
		simulation.setAlive(3, 3);
		
	}
	
	public void draw() {
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.setTransform(transform);
		
		// background
		ctx.setFill(Color.LIGHTGRAY);
		ctx.fillRect(0, 0, 400, 400);
		
		// alive cells
		ctx.setFill(Color.BLACK);
		for (int y = 0; y < simulation.height; y++) {
			for (int x = 0; x < simulation.width; x++) {
				if (simulation.getState(x, y) == 1) {
					ctx.fillRect(x, y, 1, 1);
				}
			}
		}
		
		// grid lines
		ctx.setStroke(Color.GRAY);
		ctx.setLineWidth(0.05);
		for (int y = 0; y <= simulation.height; y++) {
			ctx.strokeLine(0, y, simulation.width, y);
		}
		for (int x = 0; x <= simulation.width; x++) {
			ctx.strokeLine(x, 0, x, simulation.height);
		}
		
	}
	
}
