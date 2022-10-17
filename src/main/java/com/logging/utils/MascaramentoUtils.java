package com.logging.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.logging.annotation.Masked;
import com.logging.enumerator.MaskedStrategy;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
public class MascaramentoUtils {
	
	public static Map<String, MaskedStrategy> getCamposSensiveis(Object object) {
		return Stream.of(object.getClass().getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Masked.class))
				.collect(
					Collectors.toMap(Field::getName, field -> ((Masked) field.getAnnotation(Masked.class)).strategy())
				);
	}
	
	/**
	 * @param requestParameters
	 * @param camposMascaraveis
	 * @return
	 */
	public static Map<String, Object> mascararCampos(Map<String, Object> requestParameters,
			Map<String, MaskedStrategy> camposMascaraveis) {
		
		for(String campo : camposMascaraveis.keySet()) {
			MaskedStrategy strategy = camposMascaraveis.get(campo);
			String valorCampo = String.valueOf(requestParameters.get(campo));
			
			String campoMascarado = getCampoMascarado(valorCampo, strategy);
			requestParameters.put(campo, campoMascarado);
		}
		
		return requestParameters;
	}

	/**
	 * @param valorCampo
	 * @param strategy
	 * @return
	 */
	public static String getCampoMascarado(String valorCampo, MaskedStrategy strategy) {
		String valorMascarado;
		
		switch (strategy) {
		case FULL:
			valorMascarado = valorCampo.replaceAll(".", "*");
			break;

		default:
			valorMascarado = "*";
			break;
		}
		
		return valorMascarado;
	}

	
	/**
	 * Dado um objeto, identifica e retorna as propriedades mapeadas com a anotação
	 * {@link SensitiveData} e retorna em uma mapa orientado com o nome do campo
	 * sendo a chave e a {@link MaskingStrategy estratégia de mascaramento} da
	 * anotação sendo o valor.
	 * 
	 * @param object ({@link Object}) - objeto a ser mapeado.
	 * @return {@code Map} - mapa com os campos anotados com {@link SensitiveData}.
	 */
	public static Map<String, MaskingStrategy> getSensitiveFields(Object object) {
		return Stream.of(object.getClass().getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(SensitiveData.class))
				.collect(
					Collectors.toMap(Field::getName, field -> field.getAnnotation(SensitiveData.class).strategy())
				);
	}
	
	/**
	 * Dado um mapa de campos e um mapa dos campos a serem mascarados, realiza o
	 * mascaramento dos <b>campos presentes nos dois mapas</b>.
	 * 
	 * @param data            ({@link Map}) - mapa de campos e seus valores.
	 * @param sensitiveFields ({@link Map}) - mapa de campos e suas estratégias de
	 *                        mascaramento.
	 * @return {@code Map} - retorna <b>o próprio mapa de campos da requisição</b>
	 *         com o mascaramento dos campos indicados.
	 */
	public static Map<String, Object> maskFields(Map<String, Object> data, Map<String, MaskingStrategy> sensitiveFields) {
		for(String dataKey : sensitiveFields.keySet()) {
			MaskingStrategy strategy = sensitiveFields.get(dataKey);
			
			Optional<Object> optDataValue = Optional.ofNullable(data.get(dataKey));
			
			if(optDataValue.isPresent()) {
				String dataValue = String.valueOf(optDataValue.get());
				
				String maskedValue = maskValue(dataValue, strategy);
				data.put(dataKey, maskedValue);	
			}			
		}
		
		return data;
	}
}
