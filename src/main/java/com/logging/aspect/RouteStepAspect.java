package com.logging.aspect;

import java.time.Duration;
import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.logging.annotation.RouteStep;
import com.logging.context.LoggingComponent;
import com.logging.model.RouteStepModel;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Component
@Aspect
public class RouteStepAspect {

	@Autowired
	private LoggingComponent loggingComponent;
	
	@Around("@annotation (com.logging.annotation.RouteStep)")
	public void processarRouteStep(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		RouteStep routeStepAnnotation = methodSignature.getMethod().getAnnotation(RouteStep.class);

		LocalDateTime dataInicio = LocalDateTime.now();
				
		proceedingJoinPoint.proceed();
		
		LocalDateTime dataTermino = LocalDateTime.now();
		Long duracao = Duration.between(dataInicio, dataTermino).toMillis();
		
		
		String endpoint = routeStepAnnotation.endpoint();
		
		RouteStepModel routeStepModel = new RouteStepModel();
		routeStepModel.setDataInicio(dataInicio);
		routeStepModel.setDataTermino(dataTermino);
		routeStepModel.setDuracao(duracao);
		routeStepModel.setEndpoint(endpoint);
		
		loggingComponent.addRouteStep(routeStepModel);
	}	
}
