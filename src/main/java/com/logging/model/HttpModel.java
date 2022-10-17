package com.logging.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonInclude(Include.NON_EMPTY)
public abstract class HttpModel {

	private Map<String, Object> headers = new HashMap<>();
		
	private Map<String, Object> body = new HashMap<>();
		
	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	/**
	 * @return the headers
	 */
	public Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * @param key
	 * @param value
	 */
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
}
