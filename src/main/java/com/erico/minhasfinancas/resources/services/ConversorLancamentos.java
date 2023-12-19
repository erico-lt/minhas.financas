package com.erico.minhasfinancas.resources.services;


import com.erico.minhasfinancas.dto.LancamentoDTO;
import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;

public class ConversorLancamentos {
   

    public static void converterLancamentoDtoEmLancamento(Lancamento lancamento, LancamentoDTO dto) {      
        
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setMes(dto.getMes());
        lancamento.setAno(dto.getAno());
        lancamento.setValor(dto.getValor());
        lancamento.setTipo(EnumTipo.valueOf(dto.getTipo()));
        lancamento.setStatus(EnumStatus.valueOf(dto.getStatus()));
    }

    public static void converterLancamentoEmLancamentoDto(Lancamento lancamento, LancamentoDTO dto) {        
        dto.setDescricao(lancamento.getDescricao());
        dto.setMes(lancamento.getMes());
        dto.setAno(lancamento.getAno());
        dto.setValor(lancamento.getValor());
        dto.setTipo(EnumTipo.valueOfTipo(lancamento.getTipo()));
        dto.setStatus(EnumStatus.valueOfStatus(lancamento.getStatus()));       
    }
}
