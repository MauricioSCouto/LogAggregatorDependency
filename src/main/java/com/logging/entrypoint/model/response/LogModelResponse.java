package com.logging.entrypoint.model.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.logging.annotation.Masked;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
public class LogModelResponse {

	private String id;
	private String nome;
	
	@Masked
	private String cpf;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime dataCriacao;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}
	
	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}
