package br.com.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	//TODO: Datatime Ascii
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH24:MM:SS");
	
	public Date getDateFromStringAscii(String dateAscii) {
		try {
			return sdf.parse(dateAscii);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getStringAsciiFromDate(Date date) {
		return sdf.format(date);
	}
}
