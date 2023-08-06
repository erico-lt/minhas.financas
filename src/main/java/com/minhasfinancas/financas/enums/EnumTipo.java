package com.minhasfinancas.financas.enums;

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

    public EnumTipo valueOf(int code) {

        for(EnumTipo tipo: EnumTipo.values()) {
            if(tipo.getCode() == code) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Coddigo invalido, nao existe esse tipo");
    }
}
