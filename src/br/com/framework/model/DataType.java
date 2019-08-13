package br.com.framework.model;

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
	NDATETIME_UNIXEPOCH,
	/**
	 * TODO
	 */
	NDATETIME_ASCII;
	
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

	public boolean isNDateTimeUnixEpoch() {
		return NDATETIME_UNIXEPOCH.equals(this);
	}

	public boolean isNBoolean() {
		return NBOOLEAN.equals(this);
	}
}
