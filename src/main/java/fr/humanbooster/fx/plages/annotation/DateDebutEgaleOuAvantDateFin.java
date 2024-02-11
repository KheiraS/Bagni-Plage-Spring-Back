package fr.humanbooster.fx.plages.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import fr.humanbooster.fx.plages.validator.DateDebutEgaleOuAvantDateFinValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateDebutEgaleOuAvantDateFinValidator.class)
@Documented
public @interface DateDebutEgaleOuAvantDateFin {

	String message() default "La date de début doit être antérieure à la date de fin";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}