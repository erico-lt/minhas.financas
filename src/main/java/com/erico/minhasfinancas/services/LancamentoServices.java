package com.erico.minhasfinancas.services;

import java.math.BigDecimal;
import java.util.List;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;

public interface LancamentoServices {

    abstract Lancamento salvar(Lancamento lancamento);

    abstract Lancamento atualizar(Lancamento lancamento);
    
    abstract void deletar(Lancamento lancamento);

    abstract List<Lancamento> buscar(Lancamento lancamentoFiltro);
    
    abstract void atualizarStatus(Lancamento lancamento, EnumStatus status);

    abstract void validar(Lancamento lancamento);    

    abstract Lancamento obterPorId(Long id);

    abstract BigDecimal obterSaldoPorUsuario(Long id);
}
