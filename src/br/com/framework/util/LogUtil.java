package br.com.framework.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
	
	private static final Logger logger = LogManager.getLogger(LogUtil.class.getName());
	
	public static void info(String info) {
		logger.info(info);
	}
	
	public static void trace(String trace) {
		logger.trace(trace);
	}
	
	public static void error(String error) {
		logger.error(error);
	}
	
	public static void error(String error, Exception e) {
		logger.error(error, e);
	}
	
	public static void error(Exception e) {
		logger.error(e.getMessage(), e);
	}

}
