package com.erico.minhasfinancas.services.exceptions;

public class ExcecaoBancoDados extends RuntimeException{

    public ExcecaoBancoDados(String msg) {
        super(msg);
    }
}