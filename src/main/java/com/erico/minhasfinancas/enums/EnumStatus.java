package com.erico.minhasfinancas.enums;

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

    public static EnumStatus valueOf(int code) {
        for(EnumStatus status: EnumStatus.values()) {
            if(status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Coddigo invalido, nao existe esse status");
    }

    public static int valueOfStatus(EnumStatus enumStatus) {

        for(EnumStatus status: EnumStatus.values()) {
            if(status == enumStatus) {
                return status.getCode();
            }
        }
        throw new IllegalArgumentException("status invalido, nao existe esse status");
    }

}
