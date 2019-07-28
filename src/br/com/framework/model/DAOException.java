package br.com.framework.model;

public class DAOException extends Exception {

	public static final String DAO_EXCEPTION_CONNECTION_ERROR = "Não foi possível abrir a conexão: %s";

	//TODO: melhorar mensagens abaixo
	public static final String DAO_EXCEPTION_EXIST_ID = "Existe ID na entidade a ser salva.";
	public static final String DAO_EXCEPTION_NON_EXIST_ID = "Não existe ID na entidade a ser salva.";
	public static final String DAO_EXCEPTION_PARAMETER_NOT_APPLICABLE = "Parameter not applicable";

	public DAOException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public DAOException(Throwable throwable) {
		super(throwable);
	}
}
