package fr.humanbooster.fx.plages.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import fr.humanbooster.fx.plages.validator.SaisonEstivaleValidator;

@Target({ ElementType.TYPE })
@Documented
@Constraint(validatedBy = SaisonEstivaleValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SaisonEstivale {
	String message() default "La date doit être dans la saison estivale (du 1er Juin au 15 Septembre)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
