package com.logging.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ResponseModel extends HttpModel {
	
	private Integer status;

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
