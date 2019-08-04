package br.com.framework.model.util;

import static br.com.framework.model.util.DBAnnotationUtil.getEntityRepresentation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.util.ValidatorUtil;

/**
 * Classe reponsável por Gerenciar alguns operações relacionadas a AbstractEntidade.
 * @author obede
 *
 */
public abstract class EntityHandle {

	/**
	 * Retorna uma mapeamento de valores associados a instancia de <i>obj</i> e as definições da tabela. 
	 * @param obj
	 * @return
	 */
	public static List<ColumnValue> getColumnsValue(AbstractEntidade obj) {
		return getColumnsValue(obj, true);
	}

	/**
	 * Retorna uma mapeamento de valores associados a instancia de <i>obj</i> e as definições da tabela.
	 * Pode-se escolher não trazer a primary key.
	 * @param obj
	 * @param primaryKey
	 * @return
	 */
	public static List<ColumnValue> getColumnsValue(AbstractEntidade obj, boolean primaryKey) {
		EntityRepresentation repr = getEntityRepresentation(obj.getClass());
		List<ColumnValue> columnValues = new ArrayList<ColumnValue>();
		Object value;
		Field f;
		for (ColumnRepresentation c : repr.getColumns()) {
			if (!primaryKey && c.isPrimaryKey()) {
				continue;
			}
			try {
				f = c.getField();
				f.setAccessible(true);
				value = f.get(obj);
				if (ValidatorUtil.isNotEmpty(value)) {
					columnValues.add(new ColumnValue(c, value.toString()));
				} else {
					columnValues.add(new ColumnValue(c, null));//TODO: ??
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnValues;
	}

	/**
	 * TableName de uma Entidade.
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<? extends AbstractEntidade> clazz) {
		EntityRepresentation repr = getEntityRepresentation(clazz);
		//TODO: null exception
		return repr.getDbTable().tableName();
	}

	/**
	 * ColumnRepresentation de uma Entidade.
	 * @param clazz
	 * @return
	 */
	public static List<ColumnRepresentation> getTableColumns(Class<? extends AbstractEntidade> clazz) {
		EntityRepresentation repr = getEntityRepresentation(clazz);
		//TODO: null exception
		return repr.getColumns();
	}

	/**
	 * Lista com nomes das colunas de uma Entidade.
	 * @param clazz
	 * @return
	 */
	public static List<String> getTableColumnsName(Class<? extends AbstractEntidade> clazz) {
		return getTableColumnsName(clazz, true);
	}

	/**
	 * Lista com nomes das colunas de uma Entidade. Pode-se escolher não trazer a primary key.
	 * @param clazz
	 * @param primaryKey
	 * @return
	 */
	public static List<String> getTableColumnsName(Class<? extends AbstractEntidade> clazz, boolean primaryKey) {
		EntityRepresentation repr = getEntityRepresentation(clazz);
		//TODO: null exception
		return repr.getColumnsName(primaryKey);
	}

	/**
	 * Instância uma clazz.
	 * @param clazz
	 * @return
	 */
	public static <E extends AbstractEntidade> E getEntityInstance(Class<? extends AbstractEntidade> clazz) {
		try {
			return (E) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<ColumnRepresentation> getColumnsRelationOneTo(Class<? extends AbstractEntidade> clazz) {
		EntityRepresentation repr = getEntityRepresentation(clazz);
		//TODO: null exception
		return repr.getColumnsRelationOneTo();
	}

	public static List<ColumnValueRelationOneTo> getColumnsRelationOneToValues(AbstractEntidade obj) {
		List<ColumnRepresentation> columnRelations = getColumnsRelationOneTo(obj.getClass());
		List<ColumnValueRelationOneTo> columnsRelationsValue = new ArrayList<ColumnValueRelationOneTo>();
		Field f;
		for (ColumnRepresentation cr : columnRelations) {
			try {
				f = cr.getField();
				f.setAccessible(true);
				AbstractEntidade entity = (AbstractEntidade) f.get(obj);
				if (ValidatorUtil.isNotEmpty(entity)) {
					columnsRelationsValue.add(new ColumnValueRelationOneTo(cr, entity.getId()));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnsRelationsValue;
	}
}
