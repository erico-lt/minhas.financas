package com.erico.minhasfinancas.services.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.dto.LancamentoDto;
import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.services.LancamentoServices;

import jakarta.transaction.Transactional;

@Service
public class LancamentoServicesImpl implements LancamentoServices{

    @Autowired
    private LancamentoRepository lancamentoRepository;    

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) { 
        validar(lancamento);
        lancamento.setData_cadastro(Instant.now());
        lancamento.setStatus(EnumStatus.PENDENTE);
        return lancamentoRepository.save(lancamento); 
    }

    @Override
    @Transactional
    public Lancamento atualizar(Long id, LancamentoDto dto) {                
       
        Lancamento lanc = lancamentoRepository.getReferenceById(id);

        update(lanc, dto);
        validar(lanc);
        return lancamentoRepository.save(lanc);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        
        lancamentoRepository.deleteById(id);
    }

    //Testar se precisa remover o lancamento do Example
    @Override
    @Transactional 
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {

        Example<Lancamento> example = Example.of(lancamentoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
        
        return lancamentoRepository.findAll(example);
    }

    // @Override
    // public void atualizarStatus(Lancamento lancamento, EnumStatus status) {
    //     validar(lancamento);
    //     Objects.requireNonNull(lancamento.getId());
    //     lancamento.setStatus(status);
        
    //     atualizar(lancamento.getId(), lancamento);
    // }    

    @Override
    public void validar(Lancamento lancamento) {
        LocalDate date = LocalDate.now();
        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new ErroAutenticacaoException("Desçricão vazia");
        }

        if(lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new ErroAutenticacaoException("Mes do ano invalido");
        }

        if(lancamento.getAno() > date.getYear() || Integer.toString(lancamento.getAno()).length() < 4) {
            throw new ErroAutenticacaoException("Verifique o ano infomado");
        }

        if(lancamento.getValor() == null || lancamento.getValor() <= 0) {
            throw new ErroAutenticacaoException("Informe um valor valido acima de 0");
        }

        if(lancamento.getUsuario() == null) {
            throw new ErroAutenticacaoException("Usuario com informações incompletas");
        }

        if(lancamento.getTipo() == null) {
            throw new ErroAutenticacaoException("Tipo de lancamento não informado");
        } 
    } 

    public void update(Lancamento atualizar, LancamentoDto dto) {

        atualizar.setDescricao(dto.getDescricao());
        atualizar.setMes(dto.getMes());
        atualizar.setAno(dto.getAno());
        atualizar.setValor(dto.getValor());
        atualizar.setTipo(EnumTipo.valueOf(dto.getTipo()));        
    }
    
}
