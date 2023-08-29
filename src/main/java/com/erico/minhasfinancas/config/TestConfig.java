package com.erico.minhasfinancas.config;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.repositorys.LancamentoRepository;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;




@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public void run(String... args) throws Exception {
        
        
        Usuario user = new Usuario(null, "Erico","email@email.com", "2324", Instant.now(), null);
        usuarioRepository.save(user);  

        Lancamento lan = new Lancamento(null, "fim de mes", 03, 2023, 1.500, EnumTipo.RECEITA, EnumStatus.PENDENTE, user, Instant.now());

        lancamentoRepository.save(lan);      
    }

}
