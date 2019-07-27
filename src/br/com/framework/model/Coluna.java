package br.com.framework.model;

import br.com.framework.util.ValidatorUtil;

public class Coluna {
	
	private String columnName;
	
	private DataType type;
	
	private String value;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Integer getValueInteger() {
		if (type.isInteger() && ValidatorUtil.isNotEmpty(getValue())) {
			try {
				return Integer.parseInt(getValue());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	public Double getValueReal() {
		if (type.isReal() && ValidatorUtil.isNotEmpty(getValue())) {
			try {
				return Double.parseDouble(getValue());
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	
	public String getValueText() {
		if (type.isText() && ValidatorUtil.isNotEmpty(getValue())) {
			return getValue();
		}	
		return null;
	}
	
	public String getValueBlob() {
		// TODO : http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
		return null;
	}

	@Override
	public String toString() {
		return "Coluna [columnName=" + columnName + ", type=" + type + ", value=" + value + "]";
	}
}
