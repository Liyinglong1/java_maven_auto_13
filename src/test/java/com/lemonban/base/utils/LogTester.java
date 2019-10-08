package com.lemonban.base.utils;

import org.apache.log4j.Logger;

public class LogTester {
	
	private static Logger logger = Logger.getLogger(LogTester.class);

	public static void main(String[] args) {
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}

}
