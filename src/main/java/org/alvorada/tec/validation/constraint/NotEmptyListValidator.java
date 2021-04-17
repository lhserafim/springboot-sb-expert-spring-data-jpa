package org.alvorada.tec.validation.constraint;

import org.alvorada.tec.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

// Preciso implementar o ConstraintValidator
// Passar a anotation criada + o tipo do dado que precisa ser validado
public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {

        return list != null && !list.isEmpty(); // Implementação da validação
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        //String message = constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


}
