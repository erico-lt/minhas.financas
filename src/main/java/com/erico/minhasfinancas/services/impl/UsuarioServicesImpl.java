package com.erico.minhasfinancas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.repositorys.UsuarioRepository;
import com.erico.minhasfinancas.services.UsuarioServices;
import com.erico.minhasfinancas.services.exceptions.RegraNegocioException;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public UsuarioServicesImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {        
        throw new UnsupportedOperationException("Unimplemented method 'autenticar'");
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {        
        
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {        
    
        boolean exists = usuarioRepository.existsByEmail(email);
        if(exists) {
            throw new RegraNegocioException("Email já cadastrado no banco de dados");
        }
    }
    
}
