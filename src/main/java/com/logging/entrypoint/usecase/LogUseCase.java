package com.logging.entrypoint.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.logging.annotation.RouteStep;
import com.logging.context.LoggingComponent;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Component
public class LogUseCase {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoggingComponent loggingComponent;
		
	@RouteStep(endpoint = "metodo A")
	public void metodoA() throws InterruptedException, NoSuchMethodException, SecurityException {
		loggingComponent.addInternalLog("Acessando lógica do método A", LogLevel.INFO, this.getClass());
		
		Thread.sleep(2000);
	}

	@RouteStep(endpoint = "metodo B")
	public void metodoB() throws InterruptedException {
		loggingComponent.addInternalLog("Acessando lógica do método B", LogLevel.INFO, this.getClass());
		
		Thread.sleep(4000);
	}

	public void metodoC() throws InterruptedException {
		loggingComponent.addInternalLog("Acessando lógica do método C", LogLevel.INFO, this.getClass());
		
		restTemplate.exchange("http://localhost:8080/logRestTemplate", HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {});
	}
}
