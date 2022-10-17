package com.logging.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.logging.context.LoggingContext;
import com.logging.context.LoggingContextConstants;
import com.logging.context.LoggingContextFactory;
import com.logging.context.LoggingContextMap;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter implements Filter {
	
	@Value("#{'${app.logging.headers-sensiveis}'.split(',')}")
	private List<String> headersSensiveis;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
			final LoggingContext loggingContext = LoggingContextFactory.build(request, headersSensiveis);	
			
			MDC.put(LoggingContextConstants.CONTEXT_ID, loggingContext.getContextId());
						
			chain.doFilter(request, response);
			
			loggingContext.close(response);			
			
			LoggingContextMap.removerContexto(loggingContext.getContextId());
	}
}