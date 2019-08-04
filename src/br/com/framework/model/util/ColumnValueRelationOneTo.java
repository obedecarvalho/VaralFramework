package br.com.framework.model.util;

import br.com.framework.model.AbstractEntidade;

public class ColumnValueRelationOneTo {
	
	private ColumnRepresentation columnRepresentation;
	
	private Integer id;

	public ColumnValueRelationOneTo(ColumnRepresentation columnRepresentation, Integer id) {
		setId(id);
		setColumnRepresentation(columnRepresentation);
	}
	
	@SuppressWarnings("unchecked")
	public Class<? extends AbstractEntidade> getClassForRelationOneTo() {
		return (Class<? extends AbstractEntidade>)columnRepresentation.getField().getType();
	}

	public ColumnRepresentation getColumnRepresentation() {
		return columnRepresentation;
	}

	public void setColumnRepresentation(ColumnRepresentation columnRepresentation) {
		this.columnRepresentation = columnRepresentation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
