package com.logging.context;

import org.slf4j.MDC;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import com.logging.entrypoint.model.request.LogModelRequest;
import com.logging.entrypoint.model.response.LogModelResponse;
import com.logging.model.LoggingModel;
import com.logging.model.RouteStepModel;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-19
 *
 */
@Component
public class LoggingComponent {

	/**
	 * @param string
	 * @param info
	 * @param class1
	 */
	public void addInternalLog(String mensagem, LogLevel level, Class<?> origem) {
		LoggingModel loggingModel = getLoggingModel();
		loggingModel.addInternalLog(mensagem, level, origem);
	}

	/**
	 * @param logModelRequest
	 */
	public void addRequestParameters(LogModelRequest logModelRequest) {
		LoggingModel loggingModel = getLoggingModel();
		loggingModel.addRequestParameters(logModelRequest);
	}

	/**
	 * @param logModelRequest
	 */
	public void addRequestBody(LogModelRequest logModelRequest) {
		LoggingModel loggingModel = getLoggingModel();
		loggingModel.addRequestBody(logModelRequest);
	}
	
	/**
	 * @param logModelResponse
	 */
	public void addResponseBody(LogModelResponse logModelResponse) {
		LoggingModel loggingModel = getLoggingModel();
		loggingModel.addResponseBody(logModelResponse);
	}
	
	/**
	 * @param routeStepModel
	 */
	public void addRouteStep(RouteStepModel routeStepModel) {
		LoggingModel loggingModel = getLoggingModel();
		loggingModel.addRouteStep(routeStepModel);
	}

	private LoggingModel getLoggingModel() {
		String contextId = MDC.get(LoggingContextConstants.CONTEXT_ID);
		LoggingContext loggingContext = LoggingContextMap.recuperarContexto(contextId);
		
		LoggingModel loggingModel = loggingContext.getLoggingModel();
		return loggingModel;
	}
}
