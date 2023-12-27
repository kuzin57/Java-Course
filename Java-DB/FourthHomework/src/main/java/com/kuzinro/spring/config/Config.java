package com.kuzinro.spring.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Config {
	
	@Setter(AccessLevel.PUBLIC) @Getter(AccessLevel.PUBLIC) @JsonProperty("db")
	private String dbUrl;
	
	@Setter(AccessLevel.PUBLIC) @Getter(AccessLevel.PUBLIC) @JsonProperty("directory")
	private String pathToDir;
	
	@JsonProperty("log")
	private String logFile;
	
	@JsonProperty("dbUser")
	private String dbUser;
	
	@JsonProperty("dbPassword")
	private String dbPassword;
	
	public String getDbUrl() {
		return dbUrl;
	}
	
	public String getPathToDir() {
		return pathToDir;
	}
	
	public String getLogFile() {
		return logFile;
	}
	
	public String getDbUser() {
		return dbUser;
	}
	
	public String getDbPassword() {
		return dbPassword;
	}
}

