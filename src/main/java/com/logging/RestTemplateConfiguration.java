package com.logging;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.logging.interceptor.HttpRequestLoggingInterceptor;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@Configuration
public class RestTemplateConfiguration {

	@Bean
	public HttpRequestLoggingInterceptor getHttpRequestLoggingInterceptor() {
		return new HttpRequestLoggingInterceptor();
	}
	
	@Bean
	public RestTemplate getRestTemplate(HttpRequestLoggingInterceptor httpRequestLoggingInterceptor) {
		RestTemplate restTemplate = new RestTemplate();

		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

		if (CollectionUtils.isEmpty(interceptors)) {
			interceptors = new ArrayList<>();
		}
		
		interceptors.add(httpRequestLoggingInterceptor);
		restTemplate.setInterceptors(interceptors);
		
		return restTemplate;
	}
}
