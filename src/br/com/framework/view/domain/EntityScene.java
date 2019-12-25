package br.com.framework.view.domain;

import java.util.HashMap;
import java.util.Map;

import br.com.framework.controller.AbstractMBean;
import br.com.framework.core.AbstractEntidade;
import javafx.scene.Scene;
import javafx.scene.control.Menu;

public class EntityScene {
	
	public static final Integer SCENE_ADD = 1;
	public static final Integer SCENE_VIEW = 2;
	public static final Integer SCENE_DEL = 3;
	public static final Integer SCENE_EDIT = 4;
	public static final Integer SCENE_LIST = 5;
	public static final Integer SCENE_SEARCH = 6;

	private AbstractMBean mBean;
	private Map<Integer, Scene> scenes;
	private Menu menu;
	
	public EntityScene(AbstractMBean mBean) {
		setMBean(mBean);
		scenes = new HashMap<Integer, Scene>();
	}
	
	public AbstractMBean<AbstractEntidade> getMBean() {
		return mBean;
	}
	public void setMBean(AbstractMBean<AbstractEntidade> mBean) {
		this.mBean = mBean;
	}
	public Map<Integer, Scene> getScenes() {
		return scenes;
	}
	public void setScenes(Map<Integer, Scene> scenes) {
		this.scenes = scenes;
	}
	
	public void setScene(Integer sceneNumber, Scene scene) {
		scenes.put(sceneNumber, scene);
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
}
