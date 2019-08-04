package br.com.framework.model.util;

import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;

import br.com.framework.model.annotation.DataType;
import br.com.framework.util.ValidatorUtil;

/**
 * Representa o mapeamento ColumnRepresentation para o valor do atributo uma Instancia de Entidade.
 * @author obede
 *
 */
public class ColumnValue {
	/**
	 * Representação da coluna.
	 */
	private ColumnRepresentation columnRepresentation;

	/**
	 * Valor armazenado.
	 */
	private String value;

	public ColumnValue(ColumnRepresentation columnRepresentation, String value) {
		setColumnRepresentation(columnRepresentation);
		setValue(value);
	}

	public ColumnRepresentation getColumnRepresentation() {
		return columnRepresentation;
	}

	public void setColumnRepresentation(ColumnRepresentation columnRepresentation) {
		this.columnRepresentation = columnRepresentation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public DataType getType() {
		return getColumnRepresentation().getColumnType();
	}

	@Override
	public String toString() {
		return "ColumnValue [columnRepresentation=" + columnRepresentation + ", value=" + value + "]";
	}

	//TODO: melhorar retorno de value por tipo
	public Integer getValueInteger() {
		if (getType().isInteger() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueInteger(getValue());
		}
		return null;
	}
	
	public Double getValueReal() {
		if (getType().isReal() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueReal(getValue());
		}
		return null;
	}
	
	public Blob getValueBlob() {
		if (getType().isBlob() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueBlob(getValue());
		}
		return null;
	}
	
	public Date getValueDate() {
		if (getType().isNDateTime() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueDate(getValue());
		}
		return null;
	}
	
	public Long getValueUnixEpoch() {
		if (getType().isNDateTime() && ValidatorUtil.isNotEmpty(getValue())) {
			//return DataTypeUtil.getValueUnixEpoch(getValue());
			return DataTypeUtil.getValueUnixEpoch(getValue());
		}
		return null;
	}

	public Boolean getValueBoolean() {
		if (getType().isNBoolean() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueBoolean(getValue());
		}
		return null;
	}

	public Integer getValueBooleanInteger() {
		if (getType().isNBoolean() && ValidatorUtil.isNotEmpty(getValue())) {
			return DataTypeUtil.getValueBooleanInteger(getValue());
		}
		return null;
	}
}
