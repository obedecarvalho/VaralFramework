package br.com.framework.controller;

import java.util.List;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.util.LogUtil;

public abstract class AbstractMBean<T extends AbstractEntidade> {
	
	protected T obj;

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
	protected abstract void initObj();
	
	public abstract void save();
	
	public abstract void view();
	
	public abstract void edit();
	
	public abstract void novo();
	
	public abstract List<T> list();
	
	public abstract void cancel();
	
	protected void tratarErroPadrao(Exception e) {
		//TODO
		LogUtil.error(e);
	} 

}
