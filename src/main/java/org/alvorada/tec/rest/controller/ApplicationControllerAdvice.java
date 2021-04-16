package org.alvorada.tec.rest.controller;

import org.alvorada.tec.exception.RegrasNegocioException;
import org.alvorada.tec.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Usando ao invés da @ControllerAdvice para não precisar do @ResponseBody
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegrasNegocioException.class) // Defino qual é a classe que será interceptada para dar o tratamento
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegrasNegocioException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }
}


