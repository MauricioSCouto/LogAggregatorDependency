package com.logging.entrypoint.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.logging.context.LoggingComponent;
import com.logging.entrypoint.constants.PathConstants;
import com.logging.entrypoint.model.request.LogModelRequest;
import com.logging.entrypoint.model.response.LogModelResponse;
import com.logging.entrypoint.usecase.LogUseCase;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@RestController
@RequestMapping(PathConstants.LOG_URL)
public class LogController {	
	
	@Autowired
	private LogUseCase logUseCase;
		
	@Autowired
	private LoggingComponent loggingComponent;
	
	@GetMapping
	public ResponseEntity<String> getLog(LogModelRequest logModelRequest) throws Exception {	
		loggingComponent.addInternalLog("Acessando GET /logs", LogLevel.INFO, this.getClass());
		loggingComponent.addRequestParameters(logModelRequest);		
		
		logUseCase.metodoA();
		logUseCase.metodoB();
		logUseCase.metodoC();
		
		
		return ResponseEntity.ok("Payload log");
	}
	
	@PostMapping
	public ResponseEntity<LogModelResponse> createLog(@RequestBody LogModelRequest logModelRequest) {
		loggingComponent.addInternalLog("Acessando POST /logs", LogLevel.INFO, this.getClass());
		loggingComponent.addRequestBody(logModelRequest);
		
		LogModelResponse logModelResponse = createLogModel(logModelRequest);
		
		loggingComponent.addResponseBody(logModelResponse);
		
		URI logUri = UriComponentsBuilder.fromPath(PathConstants.LOG_URL)
			.build(logModelResponse.getId());
		
		return ResponseEntity.created(logUri).body(logModelResponse);
	}
	
	private LogModelResponse createLogModel(LogModelRequest logModelRequest) {
		LogModelResponse logModelResponse = new LogModelResponse();
		
		logModelResponse.setId(UUID.randomUUID().toString());
		logModelResponse.setNome(logModelRequest.getNome());
		logModelResponse.setCpf(logModelRequest.getCpf());
		logModelResponse.setDataCriacao(LocalDateTime.now());
		
		return logModelResponse;
	}
}
