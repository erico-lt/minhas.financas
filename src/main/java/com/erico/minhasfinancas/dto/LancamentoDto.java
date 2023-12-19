package com.erico.minhasfinancas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LancamentoDTO {
    
    private Long id;
    private String descricao;
    private int mes;
    private int ano;
    private BigDecimal valor;
    private int tipo;
    private int status;
    private Long usuario;
}
