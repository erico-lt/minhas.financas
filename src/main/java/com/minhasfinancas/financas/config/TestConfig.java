package com.minhasfinancas.financas.config;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.minhasfinancas.financas.entites.Usuario;
import com.minhasfinancas.financas.repositorys.UsuarioRepository;

@Configuration
@Profile("test" )
public class TestConfig implements CommandLineRunner{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
       
        Usuario u1 = new Usuario(null, "Erico", "erico.sstana@proton.me", "34323242", Instant.now(), null);

        usuarioRepository.save(u1);
    }
    
}
