package com.erico.minhasfinancas.repository;

import java.math.BigDecimal;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    TestEntityManager entityManager;

    Lancamento lancamento;

    @BeforeEach
    void creatLancamento() {
        lancamento = Lancamento.builder().descricao("teste").mes(1).ano(2023)
                        .valor(BigDecimal.valueOf(100)).tipo(EnumTipo.RECEITA).status(EnumStatus.PENDENTE)
                        .data_cadastro(Instant.now()).build();
    }

    @Test
    void deveSalvarUmLancamento() {
        lancamento = lancamentoRepository.save(lancamento);

        Assertions.assertThat(lancamento.getId()).isNotNull();
    }

    @Test
    void deveDeletarUmLancamentoNobancoDeDados() {

        entityManager.persist(lancamento);
        lancamentoRepository.delete(lancamento);

        Lancamento lancamentoTest = entityManager.find(Lancamento.class, lancamento.getId());
        Assertions.assertThat(lancamentoTest).isNull();
    }
    
}
