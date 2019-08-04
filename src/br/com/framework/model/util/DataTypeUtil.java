package br.com.framework.model.util;

import java.sql.Blob;
import java.util.Calendar;
import java.util.Date;

public class DataTypeUtil {

	//TODO: melhorar retorno de value por tipo
	public static Integer getValueInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Double getValueReal(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Blob getValueBlob(String value) {
		// TODO : http://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
		return null;
	}
	
	/**
	 * Retorna um Date com base em value. Value deve ser um Integer unixEpoch.
	 * @param value
	 * @return
	 */
	public static Date getValueDate(String value) {
		try {
			Long unixepoch = Long.parseLong(value) * 1000;
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(unixepoch);
			return calendar.getTime();
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Long getValueUnixEpoch(Date date) {
		return (Long) date.getTime()/1000;
	}

	public static Long getValueUnixEpoch(String value) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Boolean getValueBoolean(String value) {
		Integer newValue = getValueInteger(value);
		if (newValue == 1) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static Integer getValueBooleanInteger(String value) {
		Boolean newValue = Boolean.parseBoolean(value);
		if (newValue) {
			return 1;
		}
		return 0;
	}
}
