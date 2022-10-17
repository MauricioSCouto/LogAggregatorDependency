package com.logging.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public class RouteStepModel {

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime dataInicio;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime dataTermino;
	
	@JsonProperty("duracao (ms)")
	private Long duracao;
		
	private String endpoint;
	
	private Integer status;

	/**
	 * @return the dataInicio
	 */
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataTermino
	 */
	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

	/**
	 * @param dataTermino the dataTermino to set
	 */
	public void setDataTermino(LocalDateTime dataTermino) {
		this.dataTermino = dataTermino;
	}

	/**
	 * @return the duracao
	 */
	public Long getDuracao() {
		return duracao;
	}

	/**
	 * @param duracao the duracao to set
	 */
	public void setDuracao(Long duracao) {
		this.duracao = duracao;
	}

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

	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
