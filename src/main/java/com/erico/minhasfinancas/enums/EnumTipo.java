package com.erico.minhasfinancas.enums;

public enum EnumTipo {
    
    DESPESA(0),
    RECEITA(1);

    private int code;

    private EnumTipo(int code) {
        this.code = code;
    }

    public int getCode() {
        
        return this.code;
    }

    public static EnumTipo valueOf(int code) {

        for(EnumTipo tipo: EnumTipo.values()) {
            if(tipo.getCode() == code) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Codigo invalido, nao existe esse tipo");
    }

    public static int valueOfTipo(EnumTipo enumTipo) {

        for(EnumTipo tipo: EnumTipo.values()) {
            if(tipo == enumTipo) {
                return tipo.getCode();
            }
        }
        throw new IllegalArgumentException("Tipo invalido, nao existe esse tipo");
    }
}
