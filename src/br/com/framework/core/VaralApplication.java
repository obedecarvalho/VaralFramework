package br.com.framework.core;

import br.com.framework.view.util.SceneUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class VaralApplication extends Application {
	
	/*
	 * protected static void main(String[] args) { launch(args); }
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(SceneUtil.getApplicationName());
		primaryStage.setScene(SceneUtil.getStartScene());
		primaryStage.show();
	}

}
