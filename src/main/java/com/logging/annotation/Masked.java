package com.logging.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.logging.enumerator.MaskedStrategy;

/**
 * @author Mauricio Souza Couto
 * @since 2021-06-12
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Masked {
	
	MaskedStrategy strategy() default MaskedStrategy.FULL;

}
