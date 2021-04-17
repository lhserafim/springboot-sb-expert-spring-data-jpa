package org.alvorada.tec.validation;

import org.alvorada.tec.validation.constraint.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // para dizer que o alvo é um campo
@Constraint(validatedBy = NotEmptyListValidator.class) // Aponta para a classe criada
public @interface NotEmptyList {
    String message() default "A lista não pode ser vazia."; // Necessário definir uma mensagem padrão
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
