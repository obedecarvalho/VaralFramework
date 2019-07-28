package br.com.framework.model.util;

import static br.com.framework.util.ValidatorUtil.isEmpty;
import static br.com.framework.util.ValidatorUtil.isNotEmpty;
import static br.com.framework.util.ConstanteLog.LOG_CONNECTION_ERROR;
import static br.com.framework.util.ConstanteLog.LOG_CONNECTION_OPEN;
import static br.com.framework.util.ConstanteLog.LOG_CONNECTION_CLOSE;
import static br.com.framework.model.DAOException.DAO_EXCEPTION_CONNECTION_ERROR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.framework.model.DAOException;
import br.com.framework.util.LogUtil;

public class DAOUtil {
	
	private static Connection connection = null;
	
	private static final String CONNECTION_STRING = "jdbc:sqlite:%s";
	//TODO: implementar forma de passar como parametro/salvar lista de connections (usar MAP para varias conexoes)
	private static final String DEFAULT_DB_NAME = "dados/dados.db";

	public static Connection getConnection() throws DAOException {
		if (isEmpty(connection)) {
			try {
				connection = DriverManager.getConnection(String.format(CONNECTION_STRING, DEFAULT_DB_NAME));
			} catch (SQLException e) {
				throw new DAOException(e);
			}
			if (isEmpty(connection)) {
				LogUtil.error(String.format(LOG_CONNECTION_ERROR, DEFAULT_DB_NAME));
				throw new DAOException(String.format(DAO_EXCEPTION_CONNECTION_ERROR, DEFAULT_DB_NAME));
			}
			LogUtil.info(String.format(LOG_CONNECTION_OPEN, DEFAULT_DB_NAME));
		}
		return connection;
	}
	
	public static void closeConnection() throws DAOException {
		if (isNotEmpty(connection)) {
			try {
				connection.close();
				LogUtil.info(String.format(LOG_CONNECTION_CLOSE, DEFAULT_DB_NAME));
			} catch (SQLException e) {
				LogUtil.error(e);
				throw new DAOException(e);
			}
		}
	}
}
