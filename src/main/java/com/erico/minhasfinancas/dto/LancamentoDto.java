package com.erico.minhasfinancas.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LancamentoDTO {
    
    private String descricao;
    private int mes;
    private int ano;
    private BigDecimal valor;
    private int tipo;
    private int status;
    private Long usuario;
}
