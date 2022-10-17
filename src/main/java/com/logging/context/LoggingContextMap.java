package com.logging.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-19
 *
 */
public final class LoggingContextMap {

	private static final Map<String, LoggingContext> CONTEXTS = new HashMap<>();
	
	public static void adicionarContexto(String contextId, LoggingContext loggingContext) {
		CONTEXTS.put(contextId, loggingContext);
	}
	
	public static LoggingContext recuperarContexto(String contextId) {
		return CONTEXTS.get(contextId);
	}

	/**
	 * @param contextId
	 */
	public static void removerContexto(String contextId) {
		CONTEXTS.remove(contextId);
	}
	
}
