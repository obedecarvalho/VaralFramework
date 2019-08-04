package br.com.framework.model.util;

import java.lang.reflect.Field;

import br.com.framework.model.AbstractEntidade;
import br.com.framework.model.annotation.DataType;
import br.com.framework.util.ValidatorUtil;

public abstract class ColumnHandle {

	public static void setEntityValue(AbstractEntidade obj, ColumnRepresentation columnRepresentation, String value) {
		try {
			Field f = columnRepresentation.getField();
			f.setAccessible(true);
			if (ValidatorUtil.isNotEmpty(value)) {
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

}
