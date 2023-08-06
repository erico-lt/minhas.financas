package com.minhasfinancas.financas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.minhasfinancas.financas.entites.Usuario;
import com.minhasfinancas.financas.repositorys.UsuarioRepository;
import com.minhasfinancas.financas.services.UsuarioServices;

public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {        
        throw new UnsupportedOperationException("Unimplemented method 'autenticar'");
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {        
        throw new UnsupportedOperationException("Unimplemented method 'salvarUsuario'");
    }

    @Override
    public void validarEmail(String email) {        
        throw new UnsupportedOperationException("Unimplemented method 'validarEmail'");
    }
    
}
