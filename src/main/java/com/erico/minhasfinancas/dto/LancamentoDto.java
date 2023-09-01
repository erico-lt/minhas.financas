package com.erico.minhasfinancas.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LancamentoDto {
    
    private String descricao;
    private int mes;
    private int ano;
    private Double valor;
    private int tipo;
    private int status;
    private Long usuario;
}
