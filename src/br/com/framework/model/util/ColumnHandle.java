package br.com.framework.model.util;

import java.lang.reflect.Field;

import br.com.framework.core.AbstractEntidade;
import br.com.framework.core.util.ValidatorUtil;
import br.com.framework.model.DataType;
import br.com.framework.model.domain.ColumnRepresentation;

public abstract class ColumnHandle {

	/**
	 * Seta <i>value</i> em <i>obj</i> de acordo com <i>columnRepresentation</i>
	 * @param obj
	 * @param columnRepresentation
	 * @param value
	 */
	public static void setEntityValue(AbstractEntidade obj, ColumnRepresentation columnRepresentation, String value) {
		try {
			if (columnRepresentation.isRelationOneTo()) {
				setEntityRelationOneTo(obj, columnRepresentation, value);
			} else if (ValidatorUtil.isNotEmpty(value)) {
				Field f = columnRepresentation.getField();
				f.setAccessible(true);
				DataType type = columnRepresentation.getColumnType();
				if (type.isInteger()) {
					f.set(obj, DataTypeUtil.getValueInteger(value));
				} else if (type.isReal()) {
					f.set(obj, DataTypeUtil.getValueReal(value));
				} else if (type.isText()) {
					f.set(obj, value);
				} else if (type.isBlob()) {
					f.set(obj, DataTypeUtil.getValueBlob(value));
				} else if (type.isNDateTime()) {
					f.set(obj, DataTypeUtil.getValueDate(value));
				} else if (type.isNBoolean()) {
					f.set(obj, DataTypeUtil.getValueBoolean(value));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Responsável por instânciar e setar o id de uma Entidade RelationOneTo.
	 * @param obj
	 * @param columnRepresentation
	 * @param value
	 */
	private static void setEntityRelationOneTo(AbstractEntidade obj, ColumnRepresentation columnRepresentation, String value){
		if (ValidatorUtil.isNotEmpty(value)) {
			Field f = columnRepresentation.getField();
			Class<?> c = f.getType();
			if (AbstractEntidade.class.isAssignableFrom(c)) {
				// TODO: mover validação para DBAnnotationUtil??

				@SuppressWarnings("unchecked")
				Class<? extends AbstractEntidade> clazz = (Class<? extends AbstractEntidade>) c;

				// Instânciando Classe OneTo
				AbstractEntidade entity = EntityHandle.getEntityInstance(clazz);
				entity.setId(DataTypeUtil.getValueInteger(value));

				try {
					// Setando classe OneTo a Entidade
					f.setAccessible(true);
					f.set(obj, entity);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void setEntityRelationOneTo(AbstractEntidade obj, ColumnRepresentation columnRepresentation, AbstractEntidade relationEntity){
		if (ValidatorUtil.isNotEmpty(relationEntity) && ValidatorUtil.isNotEmpty(obj)) {
			try {
				Field f = columnRepresentation.getField();
				f.setAccessible(true);
				f.set(obj, relationEntity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
