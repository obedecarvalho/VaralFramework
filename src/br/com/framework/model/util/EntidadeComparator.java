package br.com.framework.model.util;

import br.com.framework.core.Entidade;

//TODO
public class EntidadeComparator {
	
	public static boolean equalsEntity(Entidade ent1, Entidade ent2) {
		if (ent1 == ent2)
			return true;
		if (ent1 == null)
			return false;
		if (ent2 == null)
			return false;
		if (ent1.getClass() != ent2.getClass())
			return false;
		return ent1.getId() == ent2.getId();
	}

}
