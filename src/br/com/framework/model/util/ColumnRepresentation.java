package br.com.framework.model.util;

import java.lang.reflect.Field;

import br.com.framework.model.annotation.DBColumn;
import br.com.framework.model.annotation.DataType;

/**
 * Classe que representa o mapeamento DBColumn <-> Field para atributos de uma entidade.
 * @author obede
 *
 */
public class ColumnRepresentation {

	/**
	 * Annotation do atributo da entidade.
	 */
	private DBColumn dbColumn;

	/**
	 * Field do atributo da entidade.
	 */
	private Field field;

	public String getColumnName() {
		return dbColumn.columnName();
	}

	public DataType getColumnType() {
		return dbColumn.dataType();
	}

	public boolean isPrimaryKey() {
		return dbColumn.primaryKey();
	}

	@Override
	public int hashCode() {
		return getDbColumn().columnName().hashCode();//TODO: column name deve ser unica por tabela
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnRepresentation other = (ColumnRepresentation) obj;
		if (!getDbColumn().columnName().equals(other.getDbColumn().columnName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ColumnRepresentation [dbColumn=" + getDbColumn().columnName() + " ("  + getDbColumn().dataType()+ ")" + ", field=" + field + "]";
	}

	public ColumnRepresentation(DBColumn dbColumn, Field field) {
		setDbColumn(dbColumn);
		setField(field);
	}

	public DBColumn getDbColumn() {
		return dbColumn;
	}

	public void setDbColumn(DBColumn dbColumn) {
		this.dbColumn = dbColumn;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

}
