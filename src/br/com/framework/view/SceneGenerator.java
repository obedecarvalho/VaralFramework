package br.com.framework.view;

import br.com.framework.controller.AbstractMBean;
import br.com.framework.view.domain.EntityScene;
import br.com.framework.view.domain.ViewRepresentation;
import br.com.framework.view.listener.SelectMenuEvent;
import br.com.framework.view.util.VWAnnotationUtil;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class SceneGenerator {
	
	public static final String MSG_ADD = "Novo";
	public static final String MSG_SEARCH = "Buscar";
	
	public static EntityScene generateScene(AbstractMBean mBean) {
		ViewRepresentation viewRepresentation = VWAnnotationUtil.getViewRepresentation(mBean);
		EntityScene sMBean = new EntityScene(mBean);
		sMBean.setMenu(generateSceneMenu(mBean, viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_ADD, generateSceneAdd(viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_DEL, generateSceneDelete(viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_EDIT, generateSceneEdit(viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_VIEW, generateSceneView(viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_LIST, generateSceneList(viewRepresentation));
//		sMBean.setScene(EntityScene.SCENE_SEARCH, generateSceneSearch(viewRepresentation));
		return sMBean;
	}
	
	private static Menu generateSceneMenu(AbstractMBean mBean, ViewRepresentation viewRepresentation) {
		Menu m = new Menu(viewRepresentation.getName());
		
		if (viewRepresentation.isExibirMenuAdd()) {
			MenuItem mi = new MenuItem(MSG_ADD);
			mi.setOnAction(new SelectMenuEvent(mBean, SelectMenuEvent.SEARCH));
			m.getItems().add(mi);
		}
		
		if (viewRepresentation.isExibirMenuSearch()) {
			MenuItem mi = new MenuItem(MSG_SEARCH);
			mi.setOnAction(new SelectMenuEvent(mBean, SelectMenuEvent.SEARCH));
			m.getItems().add(mi);
		}

		return m;
	}

	public static Scene generateSceneAdd(ViewRepresentation viewRepresentation) {
		return null;
	}
	
	public static Scene generateSceneEdit(ViewRepresentation viewRepresentation) {
		return null;
	}
	
	public static Scene generateSceneView(ViewRepresentation viewRepresentation) {
		return null;
	}
	
	public static Scene generateSceneDelete(ViewRepresentation viewRepresentation) {
		return null;
	}
	
	public static Scene generateSceneSearch(ViewRepresentation viewRepresentation) {
		return null;
	}
	
	public static Scene generateSceneList(ViewRepresentation viewRepresentation) {
		return null;
	}
}
