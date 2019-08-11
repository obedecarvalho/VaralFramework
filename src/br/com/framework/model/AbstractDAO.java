package br.com.framework.model;

import static br.com.framework.util.ConstanteLog.LOG_DB_INSERT;
import static br.com.framework.util.ConstanteLog.LOG_DB_DELETE;
import static br.com.framework.util.ConstanteLog.LOG_DB_UPDATE;
import static br.com.framework.util.ConstanteLog.LOG_DB_SELECT;
import static br.com.framework.util.ConstanteLog.LOG_DB_SELECT_ALL;
import static br.com.framework.util.ConstanteLog.LOG_DB_SELECT_CACHE;

import static br.com.framework.model.util.EntityHandle.getTableName;
import static br.com.framework.model.util.EntityHandle.getTableColumnsName;
import static br.com.framework.model.util.EntityHandle.getTableColumns;
import static br.com.framework.model.util.EntityHandle.getColumnsValue;
import static br.com.framework.model.util.EntityHandle.getEntityInstance;
import static br.com.framework.model.util.EntityHandle.getColumnsRelationOneToValues;

import static br.com.framework.util.StringUtil.listToString;
import static br.com.framework.util.StringUtil.stringMultiple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.framework.model.domain.ColumnRepresentation;
import br.com.framework.model.domain.ColumnValue;
import br.com.framework.model.domain.ColumnValueRelationOneTo;
import br.com.framework.model.domain.RecursiveSearchCache;
import br.com.framework.model.util.ColumnHandle;
import br.com.framework.model.util.DAOUtil;
import br.com.framework.util.LogUtil;
import br.com.framework.util.ValidatorUtil;

public abstract class AbstractDAO<T extends AbstractEntidade> {
	//TODO: classe para criar o arquivo de DB (.db)
	//TODO: não da para garantir ordem dos campos no insert e update

	private static final String SQL_INSERT = "INSERT INTO %s (%s) VALUES (%s);";
	private static final String SQL_UPDATE = "UPDATE %s SET %s WHERE ID = ?;";
	private static final String SQL_DELETE = "DELETE FROM %s WHERE ID = ?;";
	private static final String SQL_SELECT_ALL = "SELECT %s FROM %s;";
	private static final String SQL_SELECT_ID = "SELECT %s FROM %s WHERE ID = ?;";

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
		String tableName = getTableName(obj.getClass());
		List<String> columnsName = getTableColumnsName(obj.getClass(), false);
		String fieldsString = listToString(columnsName);
		String fieldCard = stringMultiple(SQL_MASK_FIELD, columnsName.size(), SQL_SEPARATOR_FIELD);
		List<ColumnValue> columnValues = getColumnsValue(obj, false);
		String sql = String.format(SQL_INSERT, tableName, fieldsString, fieldCard);

		try {
			//Inserção
			Connection conn = getConnection();
			PreparedStatement stmn = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i< columnValues.size(); i++) {
				setAttributeByType(stmn, columnValues.get(i), i+1);
			}
			Integer rowsInserted = stmn.executeUpdate();

			//Obtendo o ID inserido
			//TODO: extract method??
			ResultSet rs = stmn.getGeneratedKeys();
			if (rs.next()) {
				Integer id = rs.getInt(1);
				obj.setId(id);
			}

			//Log do insert
			LogUtil.info(String.format(LOG_DB_INSERT, sql, obj.getId(), rowsInserted));

			rs.close();
			stmn.close();

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
		String tableName = getTableName(obj.getClass());
		List<String> columnsName = getTableColumnsName(obj.getClass(), false);
		String fieldsString = generateFieldsKeyValue(columnsName);
		List<ColumnValue> columnValues = getColumnsValue(obj, false);
		String sql = String.format(SQL_UPDATE, tableName, fieldsString);

		try {
			//Update
			Connection conn = getConnection();
			PreparedStatement stmn = conn.prepareStatement(sql);
			for (int i = 0; i< columnValues.size(); i++) {
				setAttributeByType(stmn, columnValues.get(i), i+1);
			}
			stmn.setInt(columnValues.size() + 1, obj.getId());
			Integer rowsUpdated = stmn.executeUpdate();

			//Log do update
			LogUtil.info(String.format(LOG_DB_UPDATE, sql, obj.getId(), rowsUpdated));

			stmn.close();
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
		String tableName = getTableName(obj.getClass());
		String sql = String.format(SQL_DELETE, tableName);

		try {
			//Delete
			Connection conn = getConnection();
			PreparedStatement stmn = conn.prepareStatement(sql);
			stmn.setInt(1, obj.getId());
			Integer rowsDeleted = stmn.executeUpdate();

			//Log do Delete
			LogUtil.info(String.format(LOG_DB_DELETE, sql, obj.getId(), rowsDeleted));

			stmn.close();
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
		//TODO: usar unico comando?? usar IN??
		for (T obj : list) {
			delete(obj);
		}
	}

	public <E extends AbstractEntidade> E findById(Integer id, Class<? extends AbstractEntidade> clazz) throws DAOException {
		return findById(id, clazz, false);
	}

	public <E extends AbstractEntidade> E findById(Integer id, Class<? extends AbstractEntidade> clazz, boolean recursive) throws DAOException {
		return findById(id, clazz, recursive, recursive ? new RecursiveSearchCache() : null);
	}

	private <E extends AbstractEntidade> E findById(Integer id, Class<? extends AbstractEntidade> clazz, boolean recursive, RecursiveSearchCache cache) throws DAOException {
		if (ValidatorUtil.isEmpty(id)) {
			return null;
		}

		//Informações
		String tableName = getTableName(clazz);
		List<String> columnsName = getTableColumnsName(clazz);
		String fieldsString = listToString(columnsName);
		String sql = String.format(SQL_SELECT_ID, fieldsString, tableName);

		E result;

		//Verificando na cache
		if (ValidatorUtil.isNotEmpty(cache) && cache.contains(clazz, id)) {
			result = (E) cache.get(clazz, id);
			LogUtil.info(String.format(LOG_DB_SELECT_CACHE, sql, result.getId()));//TODO: null exception
			return result;
		}

		try {
			//Select
			Connection conn = getConnection();
			PreparedStatement stmn = conn.prepareStatement(sql);
			stmn.setInt(1, id);
			stmn.setMaxRows(1);
			ResultSet rs = stmn.executeQuery();

			//Preenchendo Entidade 
			result = getEntityInstance(clazz);
			if (rs.next()) {
				transforResultSetToEntity(rs, result);
				//Setando na cache
				cache.set(result);
			}
			//Log do select
			LogUtil.info(String.format(LOG_DB_SELECT, sql, result.getId()));//TODO: null exception

			//Recursivo
			if (recursive) {
				recursiveSearch(result, cache);
			}

			rs.close();
			stmn.close();

			return result;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public <E extends AbstractEntidade> List<E> findByIds(List<Integer> ids, Class<? extends AbstractEntidade> clazz) throws DAOException {
		//TODO
		return null;
	}

	public <E extends AbstractEntidade> List<E> findAll(Class<? extends AbstractEntidade> clazz) throws DAOException {
		return findAll(clazz, false);
	}

	public <E extends AbstractEntidade> List<E> findAll(Class<? extends AbstractEntidade> clazz, boolean recursive) throws DAOException {
		return findAll(clazz, recursive, recursive ? new RecursiveSearchCache() : null);
	}

	private <E extends AbstractEntidade> List<E> findAll(Class<? extends AbstractEntidade> clazz, boolean recursive, RecursiveSearchCache cache) throws DAOException {

		// Informações
		String tableName = getTableName(clazz);
		List<String> columnsName = getTableColumnsName(clazz);
		String fieldsString = listToString(columnsName);
		String sql = String.format(SQL_SELECT_ALL, fieldsString, tableName);

		List<E> results = new ArrayList<E>();

		try {
			//Select
			Connection conn = getConnection();
			PreparedStatement stmn = conn.prepareStatement(sql);
			ResultSet rs = stmn.executeQuery();
			E result;

			while (rs.next()) {
				//Preenchendo Entidade 
				result = getEntityInstance(clazz);
				transforResultSetToEntity(rs, result);
				results.add(result);
				//Setando na cache
				cache.set(result);
			}

			//Log do select
			LogUtil.info(String.format(LOG_DB_SELECT_ALL, sql, results.size()));

			//Recursive
			if (recursive) {
				for (E res : results) {
					recursiveSearch(res, cache);
				}
			}

			rs.close();
			stmn.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return results;
	}

	public void updateField(Class<? extends AbstractEntidade> clazz, int id, String field, String value) {
		//TODO: update unico campo
	}

	public void inactivate(T obj) {
		//TODO para AbstractEntidadeAuditavel
	}

	//=========================================
	//======== Métodos Auxiliares =============
	//=========================================

	private void recursiveSearch(AbstractEntidade obj, RecursiveSearchCache cache) throws DAOException {
		List<ColumnValueRelationOneTo> columnsValue = getColumnsRelationOneToValues(obj);
		for (ColumnValueRelationOneTo cv : columnsValue) {
			AbstractEntidade ent = findById(cv.getId(), cv.getClassForRelationOneTo(), true, cache);
			ColumnHandle.setEntityRelationOneTo(obj, cv.getColumnRepresentation(), ent);
		}
	}

	/**
	 * Irá percorre o ResultSet e recuperar os campos e salvar na Entidade.
	 * @param rs
	 * @param obj
	 * @throws DAOException 
	 */
	//TODO: implementar semelhante com apenas algumas colunas.
	private void transforResultSetToEntity(ResultSet rs, AbstractEntidade obj) throws DAOException {
		List<ColumnRepresentation> columnRepresentations = getTableColumns(obj.getClass());
		for (ColumnRepresentation cr : columnRepresentations) {
			String columnName = cr.getColumnName();
			try {
				String value = rs.getString(columnName);
				ColumnHandle.setEntityValue(obj, cr, value);
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}

	/**
	 * Gera String com a combinação 'campo = ?' para todas as colunas da tabela, separados por SQL_SEPARATOR_FIELD.
	 * 
	 * @param columns
	 * @return
	 */
	private String generateFieldsKeyValue(List<String> columns) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (i > 0) {
				sb.append(SQL_SEPARATOR_FIELD);
			}
			sb.append(String.format(SQL_FIELD, columns.get(i)));
		}
		return sb.toString();
	}

	/**
	 * Seta em <i>stmn<i> o valor e tipo de atributos que devem ser passados ao Statement. 
	 * @param stmn
	 * @param column
	 * @param pos
	 * @throws DAOException
	 */
	private void setAttributeByType(PreparedStatement stmn, ColumnValue column, int pos) throws DAOException {
		try {
			if (ValidatorUtil.isEmpty(column.getValue())) {
				stmn.setNull(pos, Types.NULL);
			} else if (column.getType().isInteger()) {
				stmn.setInt(pos, column.getValueInteger());
			} else if (column.getType().isReal()) {
				stmn.setDouble(pos, column.getValueReal());
			} else if (column.getType().isText()) {
				stmn.setString(pos, column.getValue());
			} else if (column.getType().isBlob()) {
				stmn.setBlob(pos, column.getValueBlob());
			} else if (column.getType().isNDateTime()) {
				stmn.setLong(pos, column.getValueUnixEpoch());
			} else if (column.getType().isNBoolean()) {
				stmn.setInt(pos, column.getValueBooleanInteger());
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

	protected Connection getConnection() throws DAOException{
		return DAOUtil.getInstance().getConnection();
	}
}
