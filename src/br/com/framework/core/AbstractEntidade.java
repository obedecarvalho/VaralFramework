package br.com.framework.core;

import br.com.framework.model.DataType;
import br.com.framework.model.annotation.DBColumn;

public abstract class AbstractEntidade {

	@DBColumn(columnName="id", dataType=DataType.INTEGER, primaryKey=true)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	abstract public boolean validate();

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntidade other = (AbstractEntidade) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
