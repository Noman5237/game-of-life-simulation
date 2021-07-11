package org.noman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MainView mainView = new MainView();
		Scene scene = new Scene(mainView, 640, 480);
		primaryStage.setScene(scene);
		primaryStage.show();
		mainView.draw();
	}
}
