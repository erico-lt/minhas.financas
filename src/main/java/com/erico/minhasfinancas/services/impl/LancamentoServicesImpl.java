package com.erico.minhasfinancas.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.exceptions.RegraNegocioException;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.services.LancamentoServices;

import jakarta.transaction.Transactional;

@Service
public class LancamentoServicesImpl implements LancamentoServices {

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
    public Lancamento atualizar(Lancamento lancamento) {
        
        try {
            Objects.requireNonNull(lancamento.getId());
            validar(lancamento);
            return lancamentoRepository.save(lancamento);

        } catch (NullPointerException e) {
            throw new NullPointerException("Id Vazio");
        }
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {

        if (lancamento.getId() == null || lancamento.getId() <= 0) {
            throw new RegraNegocioException("Lancamento nao encontrado");
        }
        lancamentoRepository.deleteById(lancamento.getId());
    }

    @Override
    @Transactional
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {

        Example<Lancamento> example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

        return lancamentoRepository.findAll(example);
    }

    @Override
    public Lancamento atualizarStatus(Lancamento lancamento, EnumStatus status) {

        Objects.requireNonNull(lancamento.getId());
        lancamento.setStatus(status);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {
        LocalDate date = LocalDate.now();
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new ErroAutenticacaoException("Desçricão vazia");
        }

        if (lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new ErroAutenticacaoException("Mes do ano invalido");
        }

        if (lancamento.getAno() > date.getYear() || Integer.toString(lancamento.getAno()).length() < 4) {
            throw new ErroAutenticacaoException("Ano invalido");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new ErroAutenticacaoException("Valor invalido, informe um acima de 0");
        }

        if (lancamento.getUsuario() == null) {
            throw new ErroAutenticacaoException("Usuario com informações invalida ou incompletas");
        }

        if (lancamento.getTipo() == null) {
            throw new ErroAutenticacaoException("Tipo de lancamento não informado ou invalido");
        }
    }

    @Override
    public Lancamento obterPorId(Long id) {

        Optional<Lancamento> obj = lancamentoRepository.findById(id);

        return obj.orElseThrow(() -> new RegraNegocioException("Lancamento nao encontrado"));
    }

    @Override
    @Transactional
    public BigDecimal obterSaldoPorUsuario(Long id) {

        BigDecimal despesa = lancamentoRepository.oberSaldoPorLancamentoEUsuario(id, EnumTipo.DESPESA);
        BigDecimal receita = lancamentoRepository.oberSaldoPorLancamentoEUsuario(id, EnumTipo.RECEITA);

        if (despesa == null) {
            despesa = BigDecimal.ZERO;
        }

        if (receita == null) {
            receita = BigDecimal.ZERO;
        }

        return receita.subtract(despesa);
    }

}
