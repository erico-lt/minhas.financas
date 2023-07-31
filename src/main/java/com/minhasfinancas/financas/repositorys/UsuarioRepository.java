package com.minhasfinancas.financas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minhasfinancas.financas.entites.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
    
}
