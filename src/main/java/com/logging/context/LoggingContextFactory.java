package com.logging.context;

import java.util.List;
import java.util.UUID;

import javax.servlet.ServletRequest;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-14
 *
 */
public class LoggingContextFactory {

	/**
	 * @param request 
	 * @param headersSensiveis 
	 * @return
	 */
	public static LoggingContext build(ServletRequest request, List<String> headersSensiveis) {
		String contextId = UUID.randomUUID().toString();
		
		LoggingContext loggingContext = new LoggingContext(contextId, headersSensiveis);
		loggingContext.start(request);
		
		LoggingContextMap.adicionarContexto(contextId, loggingContext);
				
		return loggingContext;
	}
}
