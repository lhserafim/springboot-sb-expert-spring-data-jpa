package org.alvorada.tec.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors;

    // recebe uma lista de strings
    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    // Esse construtor recebe uma mensagem e transforma em uma lista de strings
    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }
}
