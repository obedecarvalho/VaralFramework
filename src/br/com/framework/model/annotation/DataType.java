package br.com.framework.model.annotation;

public enum DataType {
	INTEGER, REAL, TEXT, BLOB,
	/**
	 * Emulação de nulo
	 */
	_NULL,
	/**
	 * Type não oficial, variação de INTEGER (unixepcok)
	 */
	_DATE;
	
	public boolean isInteger() {
		return INTEGER.equals(this);
	}
	
	public boolean isReal() {
		return REAL.equals(this);
	}
	
	public boolean isBlob() {
		return BLOB.equals(this);
	}
	
	public boolean isText() {
		return TEXT.equals(this);
	}

	public boolean isDate() {
		return _DATE.equals(this);
	}
}
