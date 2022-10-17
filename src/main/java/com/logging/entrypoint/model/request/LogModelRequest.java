package com.logging.entrypoint.model.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.logging.annotation.Masked;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
public class LogModelRequest {

	private String nome;
	
	@Masked
	private String cpf;
	

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
	
}
