package com.erico.minhasfinancas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erico.minhasfinancas.entites.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
 
    
     boolean existsByEmail(String email);
}    
