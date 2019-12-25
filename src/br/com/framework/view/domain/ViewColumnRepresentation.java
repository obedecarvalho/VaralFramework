package br.com.framework.view.domain;

import java.lang.reflect.Field;
import br.com.framework.view.annotation.VWColumn;

public class ViewColumnRepresentation {

	/**
	 * Annotation do atributo da entidade.
	 */
	private VWColumn vwColumn;

	/**
	 * Field do atributo da entidade.
	 */
	private Field field;
	
	public ViewColumnRepresentation(VWColumn vwColumn, Field field) {
		setField(field);
		setVWColumn(vwColumn);
	}

	public VWColumn getVWColumn() {
		return vwColumn;
	}

	public void setVWColumn(VWColumn dbColumn) {
		this.vwColumn = dbColumn;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}
