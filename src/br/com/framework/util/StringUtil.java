package br.com.framework.util;

public class StringUtil {

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

}
