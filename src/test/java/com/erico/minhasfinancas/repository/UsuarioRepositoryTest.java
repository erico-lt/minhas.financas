package com.erico.minhasfinancas.repository;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;


@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {   
   
    @Autowired	
	private UsuarioRepository usuarioRepository;

	@Autowired
	TestEntityManager testEntityManager;

	@Test
	void validarSeEmailExisteNoBanco() {
		Usuario usuario = criarUsuario();
		
		testEntityManager.persist(usuario);
		boolean exist = usuarioRepository.existsByEmail("erico.blp@gmail.com");		

		Assertions.assertThat(exist).isTrue();
	}

	@Test
	void retornaFalsoQaundoNaoTemUsuarioCadastradoComEsteEmail() {

		boolean result = usuarioRepository.existsByEmail("erico.blp@gmail.com");

		Assertions.assertThat(result).isFalse();
	}  
	
	@Test
	void deveExistirUmUsuarioNoBancoDeDados() {
		
		Usuario usuario = criarUsuario();
		usuarioRepository.save(usuario);		

		Assertions.assertThat(usuario.getId()).isNotNull();
	}

	@Test
	void deveBuscarUsuarioPomOEmailTestado() {
		
		Usuario usuario = criarUsuario();
		testEntityManager.persist(usuario);

		Optional<Usuario> us = usuarioRepository.findByEmail("erico.blp@gmail.com");

		Assertions.assertThat(us.isPresent()).isTrue();
	}

	@Test
	void deveRetornarFalsoSeNaoTiverUmUsuarioComOEmailTestado() {
		
		Usuario usuario = criarUsuario();
		testEntityManager.persist(usuario);

		Optional<Usuario> us = usuarioRepository.findByEmail("erico@gmail.com");

		Assertions.assertThat(us.isPresent()).isFalse();
	}

	public static Usuario criarUsuario() {

		Usuario usuario = Usuario.builder().nome("Erico").email("erico.blp@gmail.com").build();
		return usuario;
	}
}
