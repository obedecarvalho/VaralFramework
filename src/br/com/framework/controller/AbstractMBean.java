package br.com.framework.controller;

import java.util.List;

import br.com.framework.core.AbstractEntidade;
import br.com.framework.core.util.LogUtil;
import javafx.scene.Scene;

public abstract class AbstractMBean<T extends AbstractEntidade> {
	
	private T obj;
	
	private Operacao operacaoAtiva;
	
	public AbstractMBean() {
		initObj();
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}
	
	protected Operacao getOperacaoAtiva() {
		return operacaoAtiva;
	}

	protected void setOperacaoAtiva(Operacao operacaoAtiva) {
		this.operacaoAtiva = operacaoAtiva;
	}	
	
	protected boolean isOperacaoAtiva(Operacao operacao) {
		return operacao.equals(getOperacaoAtiva());
	}
	
	protected boolean isOperacaoAtivaAdd() {
		return isOperacaoAtiva(Operacao.OP_ADD_OBJ);
	}
	
	protected boolean isOperacaoAtivaEdit() {
		return isOperacaoAtiva(Operacao.OP_EDIT_OBJ);
	}
	
	protected boolean isOperacaoAtivaDel() {
		return isOperacaoAtiva(Operacao.OP_DEL_OBJ);
	}
	
	//################ ACOES ####################
	


	public Scene initAddObj() {
		setOperacaoAtiva(Operacao.OP_ADD_OBJ);
		return null;//TODO
	}
	
	public Scene initDelete() {
		setOperacaoAtiva(Operacao.OP_DEL_OBJ);
		return null;//TODO
	}
	
	public Scene initEdit() {
		setOperacaoAtiva(Operacao.OP_EDIT_OBJ);
		return null;//TODO
	}
	
	protected Scene save() {
		if (!preSaveValidate()) {
			return null;//ERRO -> mensagens de erro
		}
		//TODO: Movimento/Processador
		return null;
	}
	
	protected boolean preSaveValidate() {
		//TODO: deve ser criada pelo programador
		return true;
	}
	
	public Scene initSearch() {
		//TODO: criar padrão de busca (campos buscaveis, tipos de buscas(igual, contem, entre, ordernar por))  
			//-> criar objeto com opcoes de busca (filtro)?
		return null;
	}
	
	public Scene search() {
		//TODO: tabela com limite, descricao seguida de opções (new, save, edit, delete)
		return null;
	}
	
	protected void initObj() {
		//TODO
	}
	
	//public abstract void save();
	
	public Scene view() {
		//TODO
		return null;
	}
	
	public Scene cancel() {
		//TODO
		return null;
	}
	
	protected void tratarErroPadrao(Exception e) {
		//TODO
		LogUtil.error(e);
	} 

}
