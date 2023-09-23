package com.erico.minhasfinancas.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.exceptions.RegraNegocioException;
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
        lancamento = Lancamento.builder().descricao("Pagamento do aluguel").mes(1).ano(2023)
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

    @Test
    void deveRetornarUmaExcecaoCasoValidarcaoFalhar() {
        ErroAutenticacaoException e = assertThrows(ErroAutenticacaoException.class, () -> {

            lancamentoServices.validar(lancamento);
        });
        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    void deveAtualizarUmLancamento() {
        lancamento.setId(1L);
        Mockito.doNothing().when(lancamentoServices).validar(lancamento);

        Mockito.when(lancamentoRepository.save(lancamento)).thenReturn(lancamento);
        lancamento.setDescricao("Dividas");
        Lancamento lancamentoTest = lancamentoServices.atualizar(lancamento);
        Assertions.assertThat(lancamentoTest.getDescricao()).isEqualTo(lancamento.getDescricao());
    }

    @Test
    void deveLancarUmaExceptionQuandoAtualizarUmLancamento() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {

            lancamentoServices.atualizar(lancamento);
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Id Vazio");
    }

    @Test
    void deveDeletarUmLancamentoNoBancoDeDados() {

        lancamento.setId(1L);
        lancamentoServices.deletar(lancamento);

        Mockito.verify(lancamentoRepository, Mockito.times(1)).deleteById(lancamento.getId());
    }

    @Test
    void deveLancarUmaExcecaoQuandoTentarDeletarUmLancamento() {
        RegraNegocioException e = assertThrows(RegraNegocioException.class, () -> {

            lancamentoServices.deletar(lancamento);

            Mockito.verify(lancamentoRepository, Mockito.never()).deleteById(lancamento.getId());
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Lancamento nao encontrado");
    }

    @Test
    void deveRetonarUmaListaDeLancamentos() {

        List<Lancamento> list = Arrays.asList(lancamento);

        Mockito.when(lancamentoRepository.findAll(Mockito.<Example<Lancamento>>any())).thenReturn(list);

        List<Lancamento> listVerify = new ArrayList<>();
        listVerify = lancamentoServices.buscar(lancamento);

        Assertions.assertThat(listVerify).hasSize(1).isNotNull().contains(lancamento);
    }

    @Test
    void deveAtualizarOStatusDeUmLancamento() {
        lancamento.setId(1L);

        lancamentoServices.atualizarStatus(lancamento, EnumStatus.EFETIVADO);

        Mockito.verify(lancamentoRepository, Mockito.times(1)).save(lancamento);
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(EnumStatus.EFETIVADO);
    }

    @Test
    void deveLancarUmaExcecaoQuandoTentarMudarStatusDeUmLancamento() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            lancamentoServices.atualizarStatus(lancamento, EnumStatus.EFETIVADO);
            Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamento);
        });

        Assertions.assertThat(e.getStackTrace()).isNotEmpty();
    }

    @Test
    void naoDeveLancarUmaExcecaoQuandoValidarUmLancamento() {
        Mockito.doNothing().when(lancamentoServices).validar(lancamento);

        lancamentoServices.validar(lancamento);
    }

    @Test
    void deveLancarUmaExcecaoAoValidarLancamento() {
        ErroAutenticacaoException e = assertThrows(ErroAutenticacaoException.class, () -> {
            lancamento.setDescricao("");
            lancamentoServices.validar(lancamento);
        });

        Assertions.assertThat(e.getMessage()).isNotNull();
    }

    @Test
    void deveRetornarUmLancamentoPassandoSeuId() {
        lancamento.setId(1L);
        Mockito.when(lancamentoRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(lancamento));

        Lancamento test = lancamentoServices.obterPorId(1L);

        Assertions.assertThat(test.getId()).isEqualTo(lancamento.getId());
        Assertions.assertThat(test.getDescricao()).isEqualTo(lancamento.getDescricao());
        Assertions.assertThat(test.getMes()).isEqualTo(lancamento.getMes());
        Assertions.assertThat(test.getAno()).isEqualTo(lancamento.getAno());

    }

    @Test
    void deveLancarUmaExcecaoAoTentarBuscarPorId() {
        RegraNegocioException e = assertThrows(RegraNegocioException.class, () -> {
            lancamento.setId(1L);            

            Lancamento test = lancamentoServices.obterPorId(2L);  

            
            Assertions.assertThat(test.getId()).isNotEqualTo(lancamento.getId());
            Assertions.assertThat(test.getDescricao()).isNotEqualTo(lancamento.getDescricao());         
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Lancamento nao encontrado");
    }

}
