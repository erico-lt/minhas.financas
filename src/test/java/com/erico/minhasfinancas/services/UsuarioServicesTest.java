package com.erico.minhasfinancas.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;

@SpringBootTest
public class UsuarioServicesTest {   
   
    @Autowired	
	private UsuarioRepository usuarioRepository;

	@Test
	void validarSeEmailExisteNoBanco() {
		Usuario usuario = Usuario.builder().nome("Érico").email("erico.blp@gmail.com").build();
		
		usuarioRepository.save(usuario);
		boolean exist = usuarioRepository.existsByEmail("erico.blp@gmail.com");		

		Assertions.assertThat(exist).isTrue();
	}
    
}
