package br.com.framework.model.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.model.annotation.Coluna;
import br.com.framework.model.annotation.DBColumn;
import br.com.framework.model.annotation.DBTable;
import br.com.framework.util.ValidatorUtil;

/**
 * Classe utilizada para facilitar a manipulação de Annotation declaradas em classes.
 * @author obede
 *
 */
public class AnnotationUtil {
	
	/**
	 * Retorna o mapeamento de DBColumn declarada em uma entidade e o Field correspondente.
	 * Não busca por campos da super classe (AbstractEntidade).
	 *
	 * @param clazz
	 * @param superClazz
	 * @return
	 */
	static public Map<DBColumn,Field> getDBColumns(Class<? extends AbstractEntidade> clazz) {
		return getDBColumns(clazz, false);
	}

	/**
	 * Retorna o mapeamento de DBColumn declarada em uma entidade e o Field correspondente.
	 * Se superClazz == true busca por campos da super classe (AbstractEntidade).
	 *
	 * @param clazz
	 * @param superClazz
	 * @return
	 */
	static public Map<DBColumn,Field> getDBColumns(Class<? extends AbstractEntidade> clazz, boolean superClazz) {
		if (ValidatorUtil.isEmpty(clazz)) {
			return null;
		}

		Map<DBColumn,Field> dbColumns = new HashMap<DBColumn,Field>();
		Field[] fields = null;

		// Buscar campos da Super Classe
		if (superClazz) {
			fields = AbstractEntidade.class.getDeclaredFields();
			
			for (Field f : fields) {
				//se for column declarada
				if (f.isAnnotationPresent(DBColumn.class)) {
					DBColumn dbColumn = f.getAnnotation(DBColumn.class);
					dbColumns.put(dbColumn, f);			
				}
			}
		}

		fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			//se for column declarada
			if (f.isAnnotationPresent(DBColumn.class)) {
				DBColumn dbColumn = f.getAnnotation(DBColumn.class);
				dbColumns.put(dbColumn, f);
			}
		}		
		return dbColumns;		
	}
	
	/**
	 * Busca a annotation de uma Classe específica.
	 * @param clazz
	 * @return
	 */
	static private DBTable getDBTable(Class<? extends AbstractEntidade> clazz) {
		return clazz.getDeclaredAnnotation(DBTable.class);
	}

	/**
	 * Retorna o tableName da clazz.
	 *
	 * O tableName é definido na annotation DBTable.
	 * A clazz deve ser da hierarquia de AbstractEntidade.
	 *
	 * @param clazz
	 * @return
	 */
	static public String getDBTableName(Class<? extends AbstractEntidade> clazz) {
		DBTable dbTable = getDBTable(clazz);
		if (ValidatorUtil.isNotEmpty(dbTable)) {
			return dbTable.tableName();
		}
		return null;
	}

	/**
	 * Busca todos os campos de uma Entidade e retorna a relação de Coluna (columnName, value, type).
	 * Não busca pelos campos da superClasse (AbstractEntidade)
	 *
	 * @param obj
	 * @return
	 */
	static public List<Coluna> getColumnValues(AbstractEntidade obj){
		return getColumnValues(obj, false);
	}

	/**
	 * Busca todos os campos de uma Entidade e retorna a relação de Coluna (columnName, value, type).
	 * Se superClazz == true busca pelos campos da superClasse (AbstractEntidade)
	 *
	 * @param obj
	 * @param superClazz
	 * @return
	 */
	static public List<Coluna> getColumnValues(AbstractEntidade obj, boolean superClazz){
		//TODO: mudar tratamento exception
		List<Coluna> colunas = new ArrayList<Coluna>();
		Map<DBColumn,Field> columns = getDBColumns(obj.getClass(), superClazz);
		Coluna c = null;

		for (DBColumn dbColumn : columns.keySet()) {
			c = new Coluna();
			c.setColumnName(dbColumn.columnName());
			c.setType(dbColumn.dataType());
			try {
				Field f = columns.get(dbColumn);
				f.setAccessible(true);
				if (ValidatorUtil.isNotEmpty(f.get(obj))) {
					Object value = f.get(obj);
					if (ValidatorUtil.isNotEmpty(value)) {
						c.setValue(value.toString());
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			colunas.add(c);
		}
		return colunas;
	}

}
