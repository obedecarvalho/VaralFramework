package br.com.framework.model;

import static br.com.framework.util.ConstanteLog.LOG_DB_INSERT;
import static br.com.framework.util.ConstanteLog.LOG_DB_DELETE;
import static br.com.framework.util.ConstanteLog.LOG_DB_UPDATE;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.framework.model.annotation.Coluna;
import br.com.framework.model.annotation.DBColumn;
import br.com.framework.model.util.AnnotationUtil;
import br.com.framework.model.util.DAOUtil;
import br.com.framework.util.LogUtil;
import br.com.framework.util.StringUtil;
import br.com.framework.util.ValidatorUtil;

public abstract class AbstractDAO<T extends AbstractEntidade> {
	//TODO: cache definicoes
	//TODO: opção de busca relacoes fk
	//TODO: classe para criar o arquivo de DB

	private static final String SQL_INSERT = "INSERT INTO %s (%s) VALUES (%s);";
	private static final String SQL_UPDATE = "UPDATE %s SET %s WHERE ID = ?;";
	private static final String SQL_DELETE = "DELETE FROM %s WHERE ID = ?;";
	private static final String SQL_SELECT_ALL = "SELECT %d FROM %s;";
	private static final String SQL_SELECT_ID = "SELECT %d FROM %s WHERE ID = ?;";

	//private static final String SQL_LAST_ROWID = "SELECT last_insert_rowid() AS row_id;";

	private static final String SQL_FIELD = "%s = ?";
	private static final String SQL_MASK_FIELD = "?";
	private static final String SQL_SEPARATOR_FIELD = ", ";

	/**
	 * Realiza a inserção de uma entidade AbstractEntidade.
	 * A entidade não deve ter ID.
	 *
	 * @param obj
	 * @throws DAOException
	 */
	public void insert(T obj) throws DAOException {
		validate(obj, true);

		//Dados da insercao
		String tableName = AnnotationUtil.getDBTableName(obj.getClass());
		List<Coluna> fields = AnnotationUtil.getColumnValues(obj);
		String fieldsString = generateFieldsInsert(fields);
		String fieldCard = StringUtil.stringMultiple(SQL_MASK_FIELD, fields.size(), SQL_SEPARATOR_FIELD);

		try {
			//Inserção
			Connection conn = DAOUtil.getConnection();
			PreparedStatement stmn = conn.prepareStatement(String.format(SQL_INSERT, tableName, fieldsString, fieldCard), Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i< fields.size(); i++) {
				setAttributeByType(stmn, fields.get(i), i+1);
			}
			Integer rowInserted;
			rowInserted = stmn.executeUpdate();

			//Obtendo o ID inserido
			ResultSet rs = stmn.getGeneratedKeys();
			if (rs.next()) {
				Integer id = rs.getInt(1);
				obj.setId(id);
			}
			rs.close();
			stmn.close();

			//Log do insert
			LogUtil.info(String.format(LOG_DB_INSERT, String.format(SQL_INSERT, tableName, fieldsString, fieldCard), obj.getId(), rowInserted));

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Realiza o salvamento de uma entidade já existente.
	 * Entidade deve ter ID.
	 *
	 * @param obj
	 * @throws DAOException
	 */
	public void update(T obj) throws DAOException {
		validate(obj, false);

		//Dados do Update
		String tableName = AnnotationUtil.getDBTableName(obj.getClass());
		List<Coluna> fields = AnnotationUtil.getColumnValues(obj);
		String fieldsString = generateFieldsUpdate(fields);

		try {
			//Update
			Connection conn = DAOUtil.getConnection();
			PreparedStatement stmn = conn.prepareStatement(String.format(SQL_UPDATE, tableName, fieldsString));
			for (int i = 0; i< fields.size(); i++) {
				setAttributeByType(stmn, fields.get(i), i+1);
			}
			stmn.setInt(fields.size(), obj.getId());
			Integer rowUpdated = stmn.executeUpdate();

			//Log do update
			LogUtil.info(String.format(LOG_DB_UPDATE, String.format(SQL_UPDATE, tableName, fieldsString), obj.getId(), rowUpdated));

		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Remove uma entidade.
	 * Entidade deve ter ID.
	 *
	 * @param obj
	 * @throws DAOException
	 */
	public void delete(T obj) throws DAOException {
		validate(obj, false);

		//Dados
		String tableName = AnnotationUtil.getDBTableName(obj.getClass());

		try {
			//Delete
			Connection conn = DAOUtil.getConnection();
			PreparedStatement stmn = conn.prepareStatement(String.format(SQL_DELETE, tableName));
			stmn.setInt(0, obj.getId());
			Integer rowDeleted = stmn.executeUpdate();

			//Log do Delete
			LogUtil.info(String.format(LOG_DB_DELETE, String.format(SQL_DELETE, tableName), obj.getId(), rowDeleted));

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	public void insert(Collection<T> list) throws DAOException {
		//TODO: usar unico comando??
		//olhar PreparedStatement.addBatch()/executeBatch;
		//conexao.setAutoCommit(false); conexao.commit();
		for (T obj : list) {
			insert(obj);
		}
	}

	public void update(Collection<T> list) throws DAOException {
		//TODO: usar unico comando??
		for (T obj : list) {
			update(obj);
		}
	}

	public void delete(Collection<T> list) throws DAOException {
		//TODO: usar unico comando?? usar IN
		for (T obj : list) {
			delete(obj);
		}
	}

	public T findById(Integer id, Class<? extends AbstractEntidade> clazz) throws DAOException, SQLException {
		Connection conn = DAOUtil.getConnection();
		String tableName = AnnotationUtil.getDBTableName(clazz);

		Map<DBColumn,Field> fields = AnnotationUtil.getDBColumns(clazz);
		String fieldsString = generateFieldsInsert(fields);
		PreparedStatement stmn = conn.prepareStatement(String.format(SQL_SELECT_ID, fieldsString, tableName));
		stmn.setInt(0, id);
		// TODO : lembrar do ID
		//continua
		return null;
	}

	public List<T> findAll(Class<? extends AbstractEntidade> clazz) {
		//TODO
		return null;
	}

	//=========================================
	//======== Métodos Auxiliares =============
	//=========================================

	/**
	 * Gera String com a combinação 'campo = valor' para todas as colunas da tabela que será updated.
	 * 
	 * @param columns
	 * @return
	 */
	private String generateFieldsUpdate(List<Coluna> columns) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (i > 0) {
				sb.append(SQL_SEPARATOR_FIELD);
			}
			Coluna c = columns.get(i);
			sb.append(String.format(SQL_FIELD, c.getColumnName()));
		}
		return sb.toString();
	}

	private String generateFieldsInsert(Map<DBColumn,Field> columns) {
		StringBuilder sb = new StringBuilder();
		Collection<DBColumn> colunas = columns.keySet();
		for (DBColumn col : colunas) {
			sb.append(SQL_SEPARATOR_FIELD);
			sb.append(col.columnName());
		}
		return sb.toString().toString().substring(1);
	}

	/**
	 * Gera String com a lista de colunas (separado por vírculas) da tabela onde será feita a inserção. 
	 * @param columns
	 * @return
	 */
	private String generateFieldsInsert(List<Coluna> columns) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (i > 0) {
				sb.append(SQL_SEPARATOR_FIELD);
			}
			Coluna c = columns.get(i);
			sb.append(c.getColumnName());
		}
		return sb.toString();
	}

	private void setAttributeByType(PreparedStatement stmn, Coluna column, int pos) throws DAOException {
		//TODO: validar not null??
		try {
			if (ValidatorUtil.isEmpty(column.getValue())) {
				stmn.setNull(pos, Types.NULL);
			} else if (column.getType().isInteger()) {
				stmn.setInt(pos, column.getValueInteger());
			} else if (column.getType().isReal()) {
				stmn.setDouble(pos, column.getValueReal());
			} else if (column.getType().isText()) {
				stmn.setString(pos, column.getValueText());
			} else if (column.getType().isBlob()) {
				// TODO stmn.setBlob(pos, column.getValueBlob());
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * Valida condições básica para executar a operação.
	 * 
	 * @param obj
	 * @param validateIDExist
	 * @throws DAOException
	 */
	private void validate(T obj, boolean validateIDExist) throws DAOException {
		if (obj == null) {
			throw new DAOException(DAOException.DAO_EXCEPTION_PARAMETER_NOT_APPLICABLE);
		}
		if (validateIDExist) {
			if (ValidatorUtil.isNotEmpty(obj.getId())) {
				throw new DAOException(DAOException.DAO_EXCEPTION_EXIST_ID);
			}
		} else {
			if (ValidatorUtil.isEmpty(obj.getId())) {
				throw new DAOException(DAOException.DAO_EXCEPTION_NON_EXIST_ID);
			}
		}
	}
}
