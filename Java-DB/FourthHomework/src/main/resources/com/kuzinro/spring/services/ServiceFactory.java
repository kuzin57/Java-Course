package com.kuzinro.spring.services;

import java.util.logging.Logger;

public class ServiceFactory {
	public static IDataReader buildStudentDataReader(Logger logger) {
		return new StudentDataReader(logger);
	}
	
	public static IDataReader buildFileDataReader() {
		return new FileDataReader();
	}
	
	public static IDbManager buildMySqlDbManager(String url, String dbUser, String dbPassword, Logger logger) {
		return new MySqlDbManager(url, dbUser, dbPassword, logger);
	}
	
	public static IDataFilter buildStudentDataFilter(Logger logger) {
		return new StudentDataFilter(logger);
	}
}
