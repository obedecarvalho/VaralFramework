package br.com.framework.view.listener;

import br.com.framework.controller.AbstractMBean;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectMenuEvent implements EventHandler<ActionEvent> {
	
	private AbstractMBean mBean;
	private Integer op;
	
	public static final Integer ADD = 1;
	public static final Integer SEARCH = 2;
	
	public SelectMenuEvent(AbstractMBean mBean, Integer op) {
		setMBean(mBean);
		setOp(op);
	}

	@Override
	public void handle(ActionEvent arg0) {
		if (ADD.equals(op)) {
			mBean.initAddObj();
		}
		if (SEARCH.equals(op)) {
			mBean.initSearch();
		}
	}

	public AbstractMBean getMBean() {
		return mBean;
	}

	public void setMBean(AbstractMBean mBean) {
		this.mBean = mBean;
	}

	public Integer getOp() {
		return op;
	}

	public void setOp(Integer op) {
		this.op = op;
	}
	
	

}
