package com.erico.minhasfinancas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.impl.UsuarioServicesImpl;
import com.erico.minhasfinancas.services.impl.exceptions.RegraNegocioException;

@ActiveProfiles("test")
@SpringJUnitConfig
public class UsuarioServicesTest {

    @SpyBean
    UsuarioServicesImpl usuarioServices;

    @MockBean
    UsuarioRepository usuarioRepository;

    Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().nome("Érico").email("email@email.com").senha("senha").id(1l).build();
    }

    @Test
    void deveRetornarUmUsuarioQueFoiSalvoNoBanco() {
        assertDoesNotThrow(() -> {
            Mockito.doNothing().when(usuarioServices).validarEmail(Mockito.anyString());

            Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

            Usuario testUser = usuarioServices.salvarUsuario(usuario);

      
            Assertions.assertThat(testUser).isNotNull();
            Assertions.assertThat(testUser.getId()).isEqualTo(usuario.getId());
            Assertions.assertThat(testUser.getEmail()).isEqualTo(usuario.getEmail());
            Assertions.assertThat(testUser.getSenha()).isEqualTo(usuario.getSenha());

        });
    }

    @Test
    void deveRetornarUmaExcecaoQuandoValidarEmail() {
        RegraNegocioException e = assertThrows(RegraNegocioException.class, () -> {

            Mockito.doThrow(RegraNegocioException.class).when(usuarioServices).validarEmail(usuario.getEmail());

            usuarioServices.salvarUsuario(usuario);

            Mockito.verify(usuarioRepository, never()).save(usuario);
        });

        Assertions.assertThat(e.getMessage()).isNull();
    }

    @Test
    void testeDeveRetornarUmUsuarioQueFoiSalvoNoBancoAposAutenticacao() {
        assertDoesNotThrow(() -> {

            Mockito.when(usuarioRepository.findByEmail("email@email.com")).thenReturn(Optional.of(usuario));

            Usuario result = usuarioServices.autenticar(usuario.getEmail(), usuario.getSenha());

            Assertions.assertThat(result).isNotNull();
        });
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoNaoEncontrarUsuarioComEmailInformado() {
        RegraNegocioException e = assertThrows(RegraNegocioException.class, () -> {
            Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar("2email@email.com", usuario.getSenha());        
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Usuario nao encontrado com email informado esta incorreto");
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoFalharQuandoSenhaNaoEstiverCorreto() {
        RegraNegocioException e = assertThrows(RegraNegocioException.class, () -> {

            Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar(usuario.getEmail(), "1234");
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Senha incorreta");
    }

    @Test
    void deveValidarEmailParaQueNãoTenhaDoismEmailsIguaisNoBanco() {

        assertDoesNotThrow(() -> {
            Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

            usuarioServices.validarEmail("email@email.com");
        });

    }

    @Test
    void deveLancarErroComEmailJaExistenteNoBanco() {
        assertThrows(RegraNegocioException.class, () -> {

            Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

            usuarioServices.validarEmail("email@email.com");
        });
    }

}
