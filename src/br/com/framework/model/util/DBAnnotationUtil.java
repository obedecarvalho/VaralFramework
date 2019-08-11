package br.com.framework.model.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.model.annotation.DBColumn;
import br.com.framework.model.annotation.DBTable;
import br.com.framework.model.domain.ColumnRepresentation;
import br.com.framework.model.domain.EntityRepresentation;
import br.com.framework.util.ValidatorUtil;

/**
 * Classe utilizada para a manipulação de Annotation declaradas em classes relacionadas ao Bancos de Dados.
 * @author obede
 *
 */
public abstract class DBAnnotationUtil {

	/**
	 * Realiza o cache das definições já processadas.
	 */
	static private Map<Class<? extends AbstractEntidade>, EntityRepresentation> cachedRepresentations;

	/**
	 * Retorna o mapeamento de DBColumn declarada em uma entidade e o Field correspondente.
	 *
	 * @param clazz
	 * @return
	 */
	static public EntityRepresentation getEntityRepresentation(Class<? extends AbstractEntidade> clazz) {
		if (ValidatorUtil.isEmpty(clazz)) {
			return null;
		}
		if (cachedRepresentations == null) {
			cachedRepresentations = new HashMap<Class<? extends AbstractEntidade>, EntityRepresentation>();
		}
		
		if(cachedRepresentations.containsKey(clazz)) {
			return cachedRepresentations.get(clazz);
		}
		
		EntityRepresentation entityRepresentation = new EntityRepresentation();
		entityRepresentation.setClazz(clazz);
		entityRepresentation.setDbTable(getDBTable(clazz));//TODO: null validation

		List<ColumnRepresentation> dbColumns = new ArrayList<ColumnRepresentation>();
		Field[] fields = null;

		// Buscar campos da Super Classe
		fields = AbstractEntidade.class.getDeclaredFields();

		for (Field f : fields) {
			//se for column declarada
			if (f.isAnnotationPresent(DBColumn.class)) {
				DBColumn dbColumn = f.getAnnotation(DBColumn.class);
				dbColumns.add(new ColumnRepresentation(dbColumn, f));	
			}
		}

		fields = clazz.getDeclaredFields();

		for (Field f : fields) {
			//se for column declarada
			if (f.isAnnotationPresent(DBColumn.class)) {
				DBColumn dbColumn = f.getAnnotation(DBColumn.class);
				dbColumns.add(new ColumnRepresentation(dbColumn, f));
			}
		}

		entityRepresentation.setColumns(dbColumns);

		cachedRepresentations.put(clazz, entityRepresentation);

		return entityRepresentation;
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
	 * Valida regras da EntityRepresentation.
	 * @return
	 */
	static private boolean validate() {
		//TODO
		/*
		 *	Validar:
		 *		Tipos de dados compativeis com tipos da Annotation.
		 *		Se Colunas com RelationOneToX são instancia de AbstractEntidade.
		 */
		return true;
	}
}
