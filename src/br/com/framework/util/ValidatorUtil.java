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
		} else if (obj instanceof Integer) {
			Integer integer = (Integer) obj;
			return isEmpty(integer);
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

	private static boolean isEmpty(Integer integer) {
		if (integer == 0) {
			return true;
		}
		return false;
	}

	private static boolean isEmpty(String string) {
		if (string.trim().isEmpty()) {
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

	//=========================================
	//======== Tipos Primitivos =============
	//=========================================

	public static boolean isEmpty(byte number) {
		if (number == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(short number) {
		if (number == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(int number) {
		if (number == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(long number) {
		if (number == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(char number) {
		if (number == '\0') {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(float number) {
		if (number == 0f) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(double number) {
		if (number == 0.0) {
			return true;
		}
		return false;
	}

}
