package br.com.framework.model.annotation;

public enum DataType {
	INTEGER, REAL, TEXT, BLOB,
	/**
	 * Emulação de nulo
	 */
	//NNULL,
	/**
	 * Emulação de boolean, variacao de INTEGER (0/1).
	 */
	NBOOLEAN,
	/**
	 * Type não oficial, variação de INTEGER (unixepcok)
	 */
	NDATETIME;
	
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

	public boolean isNDateTime() {
		return NDATETIME.equals(this);
	}

	public boolean isNBoolean() {
		return NBOOLEAN.equals(this);
	}
}
