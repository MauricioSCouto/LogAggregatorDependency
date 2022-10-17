package com.logging.interceptor;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.logging.context.LoggingComponent;
import com.logging.model.RouteStepModel;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Component
public class HttpRequestLoggingInterceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private LoggingComponent loggingComponent;
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		LocalDateTime dataInicio = LocalDateTime.now();
		
		ClientHttpResponse clientHttpResponse = execution.execute(request, body);
		
		LocalDateTime dataTermino = LocalDateTime.now();
		long duracao = Duration.between(dataInicio, dataTermino).toMillis();
			
		RouteStepModel routeStepModel = new RouteStepModel();
		routeStepModel.setDataInicio(dataInicio);
		routeStepModel.setDataTermino(dataTermino);
		routeStepModel.setDuracao(duracao);
		routeStepModel.setEndpoint(request.getURI().toString());
		routeStepModel.setStatus(clientHttpResponse.getRawStatusCode());
		
		loggingComponent.addRouteStep(routeStepModel);
		
		return clientHttpResponse;
	}
}
