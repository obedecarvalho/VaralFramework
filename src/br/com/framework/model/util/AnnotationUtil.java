package br.com.framework.model.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.model.Coluna;
import br.com.framework.model.DataType;
import br.com.framework.model.annotation.DBColumn;
import br.com.framework.model.annotation.DBTable;
import br.com.framework.util.StringUtil;
import br.com.framework.util.ValidatorUtil;

public class AnnotationUtil {
	
	static public Map<DBColumn,Field> getDBColumns(Class<? extends AbstractEntidade> clazz, boolean superClazz) {
		if (ValidatorUtil.isEmpty(clazz)) {
			return null;
		}

		Map<DBColumn,Field> dbColumns = new HashMap<DBColumn,Field>();
		Field[] fields = null;
		
		if (superClazz) {
			fields = AbstractEntidade.class.getDeclaredFields();
			
			for (Field f : fields) {
				if (f.isAnnotationPresent(DBColumn.class)) {
					DBColumn dbColumn = f.getAnnotation(DBColumn.class);
					dbColumns.put(dbColumn, f);			
				}
			}
		}

		fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			if (f.isAnnotationPresent(DBColumn.class)) {
				DBColumn dbColumn = f.getAnnotation(DBColumn.class);
				dbColumns.put(dbColumn, f);
			}
		}		
		return dbColumns;		
	}
	
	/*
	 * static public Map<DataType,List<Field>> getDBColumnsByType(Class<? extends
	 * AbstractEntidade> clazz){ Map<DBColumn,Field> dbColumns =
	 * getDBColumns(clazz); Map<DataType,List<Field>> dbColumnsByType = new
	 * HashMap<DataType, List<Field>>();
	 * 
	 * for (DataType dt : DataType.values()) { dbColumnsByType.put(dt, new
	 * ArrayList<Field>()); }
	 * 
	 * for (DBColumn dbc : dbColumns.keySet()) { List<Field> list =
	 * dbColumnsByType.get(dbc.dataType()); list.add(dbColumns.get(dbc)); }
	 * 
	 * return dbColumnsByType;
	 * 
	 * }
	 */
	
	static public DBTable getDBTable(Class<? extends AbstractEntidade> clazz) {
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
	
	static public List<Coluna> getColumnValues(AbstractEntidade obj){
		List<Coluna> colunas = new ArrayList<Coluna>();
		Map<DBColumn,Field> columns = getDBColumns(obj.getClass(), false);
		Coluna c = null;

		for (DBColumn dbColumn : columns.keySet()) {
			c = new Coluna();
			c.setColumnName(dbColumn.columnName());
			c.setType(dbColumn.dataType());
			try {
				Field f = columns.get(dbColumn);
				f.setAccessible(true);
				if (ValidatorUtil.isNotEmpty(f.get(obj))) {
					c.setValue(f.get(obj).toString());
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
