package br.com.framework.model.util;

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
		EntityRepresentation repr = DBAnnotationUtil.getEntityRepresentation(obj.getClass(), true);
		List<ColumnValue> columnValues = new ArrayList<ColumnValue>();
		Object value;
		for (ColumnRepresentation c : repr.getColumns()) {
			try {
				Field f = c.getField();
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
		EntityRepresentation repr = DBAnnotationUtil.getEntityRepresentation(clazz, true);
		//TODO: null exception
		return repr.getDbTable().tableName();
	}

	/**
	 * ColumnRepresentation de uma Entidade.
	 * @param clazz
	 * @return
	 */
	public static List<ColumnRepresentation> getTableColumns(Class<? extends AbstractEntidade> clazz) {
		EntityRepresentation repr = DBAnnotationUtil.getEntityRepresentation(clazz, true);
		//TODO: null exception
		return repr.getColumns();
	}

	/**
	 * Lista com nomes das colunas de uma Entidade.
	 * @param clazz
	 * @return
	 */
	public static List<String> getTableColumnsName(Class<? extends AbstractEntidade> clazz) {
		EntityRepresentation repr = DBAnnotationUtil.getEntityRepresentation(clazz, true);
		//TODO: null exception
		return repr.getColumnsName();
	}

}
