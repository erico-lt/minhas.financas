package com.erico.minhasfinancas.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erico.minhasfinancas.entites.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{ 
    
     boolean existsByEmail(String email);
     Optional<Usuario> findByEmail(String email);
}    
