package br.com.framework.model.util;

import java.util.ArrayList;
import java.util.List;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.model.annotation.DBTable;

/**
 * Definições de uma Entidade.
 * @author obede
 *
 */
public class EntityRepresentation {

	/**
	 * Lista de representação das colunas.
	 */
	private List<ColumnRepresentation> columns;

	/**
	 * Class da entidade.
	 */
	private Class<? extends AbstractEntidade> clazz;

	/**
	 * Annotation da entidade.
	 */
	private DBTable dbTable;

	/**
	 * Lista de nomes das colunas da entidade.
	 * @return
	 */
	public List<String> getColumnsName() {
		List<String> names = new ArrayList<String>();
		for (ColumnRepresentation cr : getColumns()) {
			names.add(cr.getColumnName());
		}
		return names;
	}

	//################################################################
	//############ Construtor e get/set default ######################
	//################################################################

	public List<ColumnRepresentation> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnRepresentation> columns) {
		this.columns = columns;
	}

	public Class<? extends AbstractEntidade> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends AbstractEntidade> clazz) {
		this.clazz = clazz;
	}

	public DBTable getDbTable() {
		return dbTable;
	}

	public void setDbTable(DBTable dbTable) {
		this.dbTable = dbTable;
	}

}
