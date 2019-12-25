package br.com.framework.view.domain;

import java.util.List;

import br.com.framework.controller.AbstractMBean;
import br.com.framework.view.annotation.VWMBean;

public class ViewRepresentation {

	Class<? extends AbstractMBean> clazz;
	VWMBean vwEntity;
	List<ViewColumnRepresentation> vwColumns;

	public Class<? extends AbstractMBean> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends AbstractMBean> clazz) {
		this.clazz = clazz;
	}

	public VWMBean getVwEntity() {
		return vwEntity;
	}

	public void setVwEntity(VWMBean vwEntity) {
		this.vwEntity = vwEntity;
	}

	public List<ViewColumnRepresentation> getVwColumns() {
		return vwColumns;
	}

	public void setVwColumns(List<ViewColumnRepresentation> vwColumns) {
		this.vwColumns = vwColumns;
	}

	public String getName() {
		return vwEntity.entityName();
	}
	
	public boolean isExibirMenuAdd() {
		return vwEntity.menuAdd();
	}
	
	public boolean isExibirMenuSearch() {
		return vwEntity.menuSearch();
	}
}
