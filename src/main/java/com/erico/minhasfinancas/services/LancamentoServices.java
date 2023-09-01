package com.erico.minhasfinancas.services;

import java.util.List;

import com.erico.minhasfinancas.dto.LancamentoDto;
import com.erico.minhasfinancas.entites.Lancamento;

public interface LancamentoServices {

    abstract Lancamento salvar(Lancamento lancamento);

    abstract Lancamento atualizar(Long id, LancamentoDto dto);
    
    abstract void deletar(Long id);

    abstract List<Lancamento> buscar(Lancamento lancamentoFiltro);
    
    // abstract void atualizarStatus(Lancamento lancamento, EnumStatus status);

    abstract void validar(Lancamento lancamento);    
}
