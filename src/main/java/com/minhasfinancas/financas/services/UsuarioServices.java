package com.minhasfinancas.financas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.minhasfinancas.financas.entites.Usuario;
import com.minhasfinancas.financas.repositorys.UsuarioRepository;

public class UsuarioServices {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        
        return usuarioRepository.findAll();
    } 

    public Usuario findById(Long id) {
        
        Optional<Usuario> user = usuarioRepository.findById(id);
        return user.get();
    }
}
