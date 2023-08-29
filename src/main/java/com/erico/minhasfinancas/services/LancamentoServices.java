package com.erico.minhasfinancas.services;

import java.util.List;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.entites.Usuario;

public interface LancamentoServices {

    abstract Lancamento salvar(Lancamento lancamento, Usuario usuario);

    abstract Lancamento atualizar(Long id, Lancamento lancamento);
    
    abstract void deletar(Long id);

    abstract List<Lancamento> buscar(Lancamento lancamentoFiltro);
    
    // abstract void atualizarStatus(Lancamento lancamento, EnumStatus status);

    abstract void validar(Lancamento lancamento);    
}
