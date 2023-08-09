package com.erico.minhasfinancas.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.exceptions.RegraNegocioException;
import com.erico.minhasfinancas.services.impl.UsuarioServicesImpl;




@SpringBootTest
@ActiveProfiles("test")
@SpringJUnitConfig
public class UsuarioServicesTest {
    
    @Autowired
    UsuarioServicesImpl usuarioServicesImpl;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void validarEmailNãoDeveContemEmailNoBanco() {        

        assertDoesNotThrow(() -> {
            usuarioRepository.deleteAll();

            usuarioServicesImpl.validarEmail("email@email.com");
        });
       
    } 

    @Test
    void deveLancarErroComEmailJaExistenteNoBanco() {
        assertThrows(RegraNegocioException.class, () -> {
            Usuario usuario = Usuario.builder().nome("Erico").email("email@email.com").build();

            usuarioRepository.save(usuario);

            usuarioServicesImpl.validarEmail("email@email.com");
        });
    }
}
