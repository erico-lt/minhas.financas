package com.erico.minhasfinancas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.erico.minhasfinancas.exceptions.RegraNegocioException;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.impl.UsuarioServicesImpl;

@ActiveProfiles("test")
@SpringJUnitConfig
public class UsuarioServicesTest {

    UsuarioServices usuarioServices;

    @MockBean
    UsuarioRepository usuarioRepository;

    Usuario usuario;

    @BeforeEach
    void setup() {
        usuarioServices = new UsuarioServicesImpl(usuarioRepository);        
        usuario = Usuario.builder().email("email@email.com").senha("12345").build();
    }

    @Test
    void testeDeveRetornarUmUsuarioQueFoiSalvoNoBanco() {
        assertDoesNotThrow(() -> {

            Mockito.when(usuarioRepository.findByEmail("email@email.com")).thenReturn(Optional.of(usuario));

            Usuario result = usuarioServices.autenticar("email@email.com", "12345");

            Assertions.assertThat(result).isNotNull();
        });
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoNaoEncontrarUsuarioComEmailInformado() {
        ErroAutenticacaoException e = assertThrows(ErroAutenticacaoException.class, () -> {            
            Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar("2email@email.com", usuario.getSenha());
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Usuario não encontrado, verifique o email");
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoFalharQuandoSenhaNaoEstiverCorreto() {
        ErroAutenticacaoException e = assertThrows(ErroAutenticacaoException.class, () -> {            

            Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar(usuario.getEmail(), "1234");
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("Senha incorreta");
    }

    @Test
    void validarEmailNãoDeveContemEmailNoBanco() {

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
