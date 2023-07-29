package com.minhasfinancas.financas.enums;

public enum EnumStatus {

    PENDENTE(0),
    CANCELADO(1),
    EFETIVADO(2);

    private int code;

    private EnumStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public EnumStatus valueOf(int code) {
        for(EnumStatus status: EnumStatus.values()) {
            if(status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Coddigo invalido, nao existe esse tipo");
    }

}
