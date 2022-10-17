package com.logging.model;

import org.springframework.boot.logging.LogLevel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
public class InternalLogModel {
	
	private String log;
	private LogLevel logLevel;
	private String classe;

	public InternalLogModel(String log, LogLevel logLevel, Class<?> clazz) {
		this.log = log;
		this.logLevel = logLevel;
		this.classe = clazz.getCanonicalName();
	}

	/**
	 * @return the log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * @return the logLevel
	 */
	public LogLevel getLogLevel() {
		return logLevel;
	}

	/**
	 * @return the classe
	 */
	public String getClasse() {
		return classe;
	}

}
