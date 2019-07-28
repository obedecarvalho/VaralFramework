package br.com.framework.util;

public abstract class ConstanteLog {

	//DB DAO
	public static final String LOG_DB_DELETE = "Delete: {sql: %s, id: %d, total: %d}";
	public static final String LOG_DB_UPDATE = "Update: {sql: %s, id: %d, total: %d}";
	public static final String LOG_DB_INSERT = "Insert: {sql: %s, id: %d, total: %d}";

	//DB CONNECTION
	public static final String LOG_CONNECTION_ERROR = "Não foi possível abrir a conexão: %s";
	public static final String LOG_CONNECTION_OPEN = "Conexão aberta: %s";
	public static final String LOG_CONNECTION_CLOSE = "Conexão fechada: %s";

	//FRAME
	public static final String LOG_FRAME_SELECT = "Frame selecionado: %s";

	//LISTENER
	public static final String LOG_LISTENER_ACTION_PERFORMED = "Action Performed: %s";
	
}
