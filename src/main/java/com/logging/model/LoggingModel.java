package com.logging.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.logging.LogLevel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.logging.enumerator.MaskedStrategy;
import com.logging.utils.MascaramentoUtils;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@JsonNaming(SnakeCaseStrategy.class)
@JsonPropertyOrder({
	"data_inicio",
	"data_termino",
	"duracao (ms)",
	"thread_name",
	"request",
	"response",
	"logs_aplicacao"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class LoggingModel {
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime dataInicio;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
	private LocalDateTime dataTermino;
	
	@JsonProperty("duracao (ms)")
	private long duracao;

	private String threadName;
	
	@JsonProperty("request")
	private RequestModel requestModel;
	
	@JsonProperty("response")
	private ResponseModel responseModel;
	
	@JsonProperty("routeSteps")
	private List<RouteStepModel> routeStepList;
	
	@JsonProperty("logs_aplicacao")
	private List<InternalLogModel> internalLogModelList;
		
	public void setup() {
		dataInicio = LocalDateTime.now();
		threadName = Thread.currentThread().getName();
		internalLogModelList = new ArrayList<>();
		requestModel = new RequestModel();
		responseModel = new ResponseModel();
		routeStepList = new ArrayList<>();
	}
	
	public void finish() {
		dataTermino = LocalDateTime.now();
		duracao = Duration.between(dataInicio, dataTermino).toMillis();
	}
	
	/**
	 * @return the dataInicio
	 */
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	/**
	 * @return the dataTermino
	 */
	public LocalDateTime getDataTermino() {
		return dataTermino;
	}

	/**
	 * @return the duracao
	 */
	public long getDuracao() {
		return duracao;
	}

	/**
	 * @return the threadName
	 */
	public String getThreadName() {
		return threadName;
	}

	/**
	 * @return the requestModel
	 */
	public RequestModel getRequestModel() {
		return requestModel;
	}
	
	public ResponseModel getResponseModel() {
		return responseModel;
	}

	/**
	 * @return the internalLogModelList
	 */
	public List<InternalLogModel> getInternalLogModelList() {
		return internalLogModelList;
	}

	/**
	 * @param internalLogModelList the internalLogs to set
	 */
	public void addInternalLog(String log, LogLevel logLevel, Class<?> clazz) {
		this.internalLogModelList.add(new InternalLogModel(log, logLevel, clazz));
	}
	
	public void addRequestHeader(String key, String value) {
		requestModel.addHeader(key, value);	
	}

	/**
	 * @param headerKey
	 * @param headerValue
	 * @param full
	 */
	public void addRequestHeader(String headerKey, String headerValue, MaskedStrategy strategy) {
		String headerMascarado = MascaramentoUtils.getCampoMascarado(headerValue, strategy);
		addRequestHeader(headerKey, headerMascarado);
	}

	public void addRequestUrl(String url) {
		requestModel.setUrl(url);
	}
	
	public void addRequestMethod(String metodo) {
		requestModel.setMetodo(metodo);
	}
	
	@SuppressWarnings("unchecked")
	public void addRequestParameters(Object parametros) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> requestParameters = objectMapper.convertValue(parametros, Map.class);
		
		Map<String, MaskedStrategy> camposSensiveis = MascaramentoUtils.getCamposSensiveis(parametros);	
		Map<String, Object> valoresMascarados = MascaramentoUtils.mascararCampos(requestParameters, camposSensiveis);
		
		requestModel.setParameters(valoresMascarados);
	}
	
	/**
	 * @param logModelRequestParameter
	 */
	@SuppressWarnings("unchecked")
	public void addRequestBody(Object body) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> requestBody = objectMapper.convertValue(body, Map.class);
		
		Map<String, MaskedStrategy> camposSensiveis = MascaramentoUtils.getCamposSensiveis(body);
		Map<String, Object> bodyPropertiesMascarado = MascaramentoUtils.mascararCampos(requestBody, camposSensiveis);
		
		requestModel.setBody(bodyPropertiesMascarado);
	}

	@SuppressWarnings("unchecked")
	public void addResponseBody(Object body) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> requestBody = objectMapper.convertValue(body, Map.class);
		
		Map<String, MaskedStrategy> camposSensiveis = MascaramentoUtils.getCamposSensiveis(body);	
		Map<String, Object> bodyPropertiesMascarado = MascaramentoUtils.mascararCampos(requestBody, camposSensiveis);
		
		responseModel.setBody(bodyPropertiesMascarado);
	}
	
	public void addResponseHeader(String key, String value) {
		responseModel.addHeader(key, value);	
	}

	/**
	 * @param headerKey
	 * @param headerValue
	 * @param full
	 */
	public void addResponseHeader(String headerKey, String headerValue, MaskedStrategy strategy) {
		String headerMascarado = MascaramentoUtils.getCampoMascarado(headerValue, strategy);
		addResponseHeader(headerKey, headerMascarado);
	}

	/**
	 * @param status
	 */
	public void addResponseStatus(Integer status) {
		responseModel.setStatus(status);
	}
	
	public void addRouteStep(RouteStepModel routeStepModel) {
		this.routeStepList.add(routeStepModel);
	}	
}
