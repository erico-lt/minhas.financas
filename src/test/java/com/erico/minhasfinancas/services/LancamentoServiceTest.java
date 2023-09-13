package com.erico.minhasfinancas.services;

import java.math.BigDecimal;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.services.impl.LancamentoServicesImpl;

@ActiveProfiles("test")
@SpringJUnitConfig
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServicesImpl lancamentoServices;

    @MockBean
    LancamentoRepository lancamentoRepository;

    Lancamento lancamento;

    @BeforeEach
    void criarLancamento() {
        lancamento = Lancamento.builder().descricao("teste").mes(1).ano(2023)
                .valor(BigDecimal.valueOf(100)).tipo(EnumTipo.RECEITA).status(EnumStatus.PENDENTE)
                .data_cadastro(Instant.now()).build();
    }

    @Test
    void deveSalvarUmLancamento() {
        Mockito.doNothing().when(lancamentoServices).validar(lancamento);

        Lancamento lancamentoSalvo = lancamento;
        lancamentoSalvo.setId(1L);
        Mockito.when(lancamentoRepository.save(lancamento)).thenReturn(lancamentoSalvo);

        lancamento = lancamentoServices.salvar(lancamento);
        
        Assertions.assertThat(lancamento.getId()).isNotNull();
        Assertions.assertThat(lancamento.getId()).isEqualTo(1);
    }
}
