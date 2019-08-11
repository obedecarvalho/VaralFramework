package br.com.framework.core.util;

import java.util.List;

/**
 * Classe com métodos comuns na manipulação de Strings.
 * @author obede
 *
 */
public class StringUtil {

	/**
	 * Separador Default.
	 */
	private static final String DEFAULT_SEPARATOR = ", ";

	/**
	 * Gera um String de number vezes o text separados pelo separator.
	 * @param text
	 * @param number
	 * @param separator
	 * @return
	 */
	static public String stringMultiple(String text, int number, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i  = 0; i < number; i++) {
			if (i > 0  && ValidatorUtil.isNotEmpty(separator)) {
				sb.append(separator);
			}
			sb.append(text);
		}
		return sb.toString();
	}

	/**
	 * Gera um String de number vezes o text.
	 * @param text
	 * @param number
	 * @return
	 */
	static public String stringMultiple(String text, int number) {
		return stringMultiple(text, number, null);
	}

	/**
	 * Gera uma String com todos os elementos da lista separados por DEFAULT_SEPARATOR.
	 * @param list
	 * @return
	 */
	static public String listToString(List<String> list) {
		return listToString(list, DEFAULT_SEPARATOR);
	}

	/**
	 * Gera uma String com todos os elementos da lista separados por separator.
	 * @param list
	 * @param separator
	 * @return
	 */
	static public String listToString(List<String> list, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}
}
