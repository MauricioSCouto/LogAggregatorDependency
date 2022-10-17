package com.logging.entrypoint.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-13
 *
 */
@RestController
@RequestMapping("/logRestTemplate")
public class LogRestTemplateController {

	@GetMapping
	public ResponseEntity<Object> getRestTemplateResponse() throws InterruptedException {
		Thread.sleep(2500);
		
		return ResponseEntity.ok().build();
	}
	
}
