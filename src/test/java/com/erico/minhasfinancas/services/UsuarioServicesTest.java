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

    @BeforeEach
    void setup() {        
        usuarioServices = new UsuarioServicesImpl(usuarioRepository);
    }

    @Test
    void testeDeveRetornarUmUsuarioQueFoiSalvoNoBanco() {
        assertDoesNotThrow(() -> {
            String email = "email@email.com";
            String senha = "12345";
            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();

            Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

            Usuario result = usuarioServices.autenticar(email, senha);

            Assertions.assertThat(result).isNotNull();
        });
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoFalharQuandoEmailNaoEstiverCorreto() {
        assertThrows(ErroAutenticacaoException.class, () -> {
            String email = "email@email.com";
            String senha = "12345";
            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();

            Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar("2email@email.com", senha);            
        });
    }

    @Test
    void testeDeveRetornarUmaExcecaoSeAutenticacaoFalharQuandoSenhaNaoEstiverCorreto() {
        assertThrows(ErroAutenticacaoException.class, () -> {
            String email = "email@email.com";
            String senha = "12345";
            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();

            Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

            usuarioServices.autenticar(email, "1234");            
        });
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
