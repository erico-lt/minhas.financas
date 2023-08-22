package com.erico.minhasfinancas.services.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.services.LancamentoServices;

import jakarta.transaction.Transactional;

public class LancamentoServicesImpl implements LancamentoServices{

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {        
        
        return lancamentoRepository.save(lancamento); 
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        
        Objects.requireNonNull(lancamento.getId());
        Lancamento lanc = lancamentoRepository.getReferenceById(lancamento.getId());

        update(lanc, lancamento);
        return lancamentoRepository.save(lanc);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {

        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.deleteById(lancamento.getId());
    }

    //Testar se precisa remover o lancamento do Example
    @Override
    @Transactional 
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {

        Example<Lancamento> example = Example.of(lancamentoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, EnumStatus status) {
        Objects.requireNonNull(lancamento.getId());
        lancamento.setStatus(status);
        
        atualizar(lancamento);
    }

    public void update(Lancamento atualizar, Lancamento auxiliar) {

        atualizar.setDescricao(auxiliar.getDescricao());
        atualizar.setMes(auxiliar.getMes());
        atualizar.setAno(auxiliar.getAno());
        atualizar.setValor(auxiliar.getValor());
        atualizar.setTipo(auxiliar.getTipo());
        
    } 
    
}
