package br.com.framework.util;

import java.util.Collection;
import java.util.Map;

import br.com.framework.model.AbstractEntidade;

public class ValidatorUtil {
	
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}
	
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			String string = (String) obj;
			return isEmpty(string);
		} else if (obj instanceof AbstractEntidade) {
			AbstractEntidade abstractEntidade = (AbstractEntidade) obj;
			return isEmpty(abstractEntidade);
		} else if (obj instanceof Collection<?>) {
			Collection<?> list = (Collection<?>) obj;
			return isEmpty(list);
		} else if (obj instanceof Map<?, ?>) {
			Map<?, ?> list = (Map<?, ?>) obj;
			return isEmpty(list);
		}
		return false;
	}
	
	private static boolean isEmpty(String string) {
		if (string.trim() == "") {
			return true;
		}
		return false;
	}

	private static boolean isEmpty(AbstractEntidade entidade) {
		if (entidade.getId() == 0) {
			return true;
		}		
		return false;		
	}
	
	private static boolean isEmpty(Collection<?> collection) {
		if (collection.isEmpty()) {
			return true;
		}		
		return false;		
	}
	
	private static boolean isEmpty(Map<?, ?> map) {
		if (map.isEmpty()) {
			return true;
		}		
		return false;		
	}

}
