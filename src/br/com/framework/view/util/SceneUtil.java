package br.com.framework.view.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.controller.AbstractMBean;
import br.com.framework.core.AbstractEntidade;
import br.com.framework.view.SceneGenerator;
import br.com.framework.view.domain.EntityScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SceneUtil {
	
	private static Integer WIDTH = 600;
	private static Integer HEIGTH = 400;
	
	private static Map<Class<? extends AbstractMBean>, EntityScene> manageBeans = new HashMap<Class<? extends AbstractMBean>, EntityScene>();
	
	public static void addManageBean(Class<? extends AbstractMBean> clazz) {
		try {
			AbstractMBean mBean = clazz.newInstance();
			EntityScene es = SceneGenerator.generateScene(mBean);
			manageBeans.put(clazz, es);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static MenuBar getMenu() {
		MenuBar mb = new MenuBar();
		mb.getMenus().add(manageBeans.values().iterator().next().getMenu());
		return mb;
	}
	
	private static Scene getScene(Pane pane) {
		//TODO: properties
		return new Scene(pane, WIDTH, HEIGTH);
	}
	
	private static Pane getPane() {
		//TODO: factory
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(getMenu());
		return pane;
	}
	
	public static String getApplicationName() {
		return "Varal";//TODO
	}
	
	public static Scene getStartScene() {
		return getScene(getPane());
	}
	
	public static Scene getViewScene(Class<? extends AbstractEntidade> clazz) {
		Pane pane = getPane();
		return null;
	}

	private void prepareApplication() {
		//carregar xml com mbens
	}
}
