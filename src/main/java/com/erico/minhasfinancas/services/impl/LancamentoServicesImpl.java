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
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.services.LancamentoServices;
import com.erico.minhasfinancas.services.impl.exceptions.RecursoNaoEncontradoException;
import com.erico.minhasfinancas.services.impl.exceptions.RegraNegocioException;

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
            throw new RecursoNaoEncontradoException(lancamento.getId());
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
    public void atualizarStatus(Lancamento lancamento, EnumStatus status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {
        LocalDate date = LocalDate.now();
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Desçricão vazia");
        }

        if (lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocioException("Mes do ano invalido");
        }

        if (lancamento.getAno() > date.getYear() || Integer.toString(lancamento.getAno()).length() < 4) {
            throw new RegraNegocioException("Ano invalido");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException("Valor invalido, informe um acima de 0");
        }

        if (lancamento.getUsuario() == null) {
            throw new RegraNegocioException("Usuario com informações invalida ou incompletas");
        }

        if (lancamento.getTipo() == null) {
            throw new RegraNegocioException("Tipo de lancamento não informado ou invalido");
        }
    }

    @Override
    public Lancamento obterPorId(Long id) {

        Optional<Lancamento> obj = lancamentoRepository.findById(id);

        return obj.orElseThrow(() -> new RecursoNaoEncontradoException(id));
    }

    @Override
    @Transactional
    public BigDecimal obterSaldoPorUsuario(Long id) {

        BigDecimal despesa = lancamentoRepository.oberSaldoPorUsuario(id, EnumTipo.DESPESA);
        BigDecimal receita = lancamentoRepository.oberSaldoPorUsuario(id, EnumTipo.RECEITA);

        if (despesa == null) {
            despesa = BigDecimal.ZERO;
        }

        if (receita == null) {
            receita = BigDecimal.ZERO;
        }

        return receita.subtract(despesa);
    }

}
