package br.upf.fabsoft.jmkidsmodel.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringOptionsValidImpl.class)
@Documented
/**
 * Exemplo de uso @StringOptionsValid(message="Op��o inv�lida!",
 * opcoes={"Fone","Fax","Celular"} )
 */
public @interface StringOptionsValid {
	String message() default "Op��o inv�lida!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] opcoes() default {};
}