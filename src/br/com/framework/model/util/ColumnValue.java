package br.com.framework.model.util;

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

	//TODO: melhorar retorno de value por tipo
	public Integer getValueInteger() {
		if (getType().isInteger() && ValidatorUtil.isNotEmpty(getValue())) {
			try {
				return Integer.parseInt(getValue());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	public Double getValueReal() {
		if (getType().isReal() && ValidatorUtil.isNotEmpty(getValue())) {
			try {
				return Double.parseDouble(getValue());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	public String getValueText() {
		if (getType().isText() && ValidatorUtil.isNotEmpty(getValue())) {
			return getValue();
		}	
		return null;
	}
	
	public String getValueBlob() {
		// TODO : http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
		return null;
	}
	
	public Date getValueDate() {
		//TODO: db datetime
		//strftime('%s','now')
		if (getType().isText() && ValidatorUtil.isNotEmpty(getValue())) {
			try {
				Integer unixepoch = Integer.parseInt(getValue());
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(unixepoch);
				return calendar.getTime();
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	public Integer getUnixEpoch() {
		if (getType().isText() && ValidatorUtil.isNotEmpty(getValue())) {
			//TODO
			return null;
		}
		return null;
	}

	@Override
	public String toString() {
		return "ColumnValue [columnRepresentation=" + columnRepresentation + ", value=" + value + "]";
	}

}
