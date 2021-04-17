package org.alvorada.tec.rest.controller;

import org.alvorada.tec.exception.PedidoNaoEncontradoException;
import org.alvorada.tec.exception.RegrasNegocioException;
import org.alvorada.tec.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // Usando ao invés da @ControllerAdvice para não precisar do @ResponseBody
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegrasNegocioException.class) // Defino qual é a classe que será interceptada para dar o tratamento
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegrasNegocioException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException( PedidoNaoEncontradoException exception) {
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    // Criando um handler p/ tratar as validações do Bean Validation e retornar o erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException exception) {
        List<String> erros = exception
                                .getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErrors(erros);
    }

}


