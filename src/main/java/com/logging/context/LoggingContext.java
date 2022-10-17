package com.logging.context;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logging.enumerator.MaskedStrategy;
import com.logging.model.LoggingModel;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
public class LoggingContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("logger");
	
	
	private ObjectMapper objectMapper = new ObjectMapper();

	private Boolean contextIniciado = false;
	private String contextId;
	
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;

	private LoggingModel loggingModel;
	private List<String> headersSensiveis;

	
	public LoggingContext(String contextId) {
		this(contextId, new ArrayList<>());
	}
	
	/**
	 * @param contextId
	 * @param headersSensiveis
	 */
	public LoggingContext(String contextId, List<String> headersSensiveis) {
		this.contextId = contextId;
		this.headersSensiveis = headersSensiveis;
	}

	/**
	 * @return the contextId
	 */
	public String getContextId() {
		return contextId;
	}
	
	public LoggingModel getLoggingModel() {
		return loggingModel;
	}

	/**
	 * 
	 */
	public void start(ServletRequest request) {
		contextIniciado = true;
		loggingModel = new LoggingModel();
		
		loggingModel.setup();
		httpServletRequest = (HttpServletRequest) request;
		
		addRequestHeaders();
		
		loggingModel.addRequestUrl(httpServletRequest.getRequestURI());
		loggingModel.addRequestMethod(httpServletRequest.getMethod());
	}

	public void close(ServletResponse response) throws JsonProcessingException {
		objectMapper.setSerializationInclusion(Include.NON_NULL);

		if(contextIniciado) {
			httpServletResponse = (HttpServletResponse) response;
			
			addResponseHeaders();
			
			loggingModel.addResponseStatus(httpServletResponse.getStatus());
			
			loggingModel.finish();
			LOGGER.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loggingModel));
		}		
	}

	private void addRequestHeaders() {
		httpServletRequest.getHeaderNames().asIterator().forEachRemaining(headerKey -> {
			String headerValue = httpServletRequest.getHeader(headerKey);
			
			if(headersSensiveis.contains(headerKey)) {
				loggingModel.addRequestHeader(headerKey, headerValue, MaskedStrategy.FULL);
			}
			else {
				loggingModel.addRequestHeader(headerKey, headerValue);
			}			
		});
	}

	private void addResponseHeaders() {
		httpServletResponse.getHeaderNames().forEach(headerKey -> {
			String headerValue = httpServletRequest.getHeader(headerKey);
			
			if(headersSensiveis.contains(headerKey)) {
				loggingModel.addResponseHeader(headerKey, headerValue, MaskedStrategy.FULL);
			}
			else {
				loggingModel.addResponseHeader(headerKey, headerValue);
			}			
		});
	}
}