package com.erico.minhasfinancas.services.impl.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public RecursoNaoEncontradoException(Object object) {
        super("Recurso nao encontrado id: " + object);
    }
}