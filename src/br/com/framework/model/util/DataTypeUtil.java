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
			Integer unixepoch = Integer.parseInt(value);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(unixepoch);
			return calendar.getTime();
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static Integer getValueUnixEpoch(String value) {
		// TODO: db datetime
		// strftime('%s','now')
		//TODO
		return null;
	}
	
	public static Boolean getValueBoolean(String value) {
		return Boolean.getBoolean(value);
	}

	public static Integer getValueBooleanInteger(String value) {
		//TODO
		return null;
	}
}
