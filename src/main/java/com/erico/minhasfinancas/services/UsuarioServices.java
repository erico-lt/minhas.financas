package com.erico.minhasfinancas.services;

import com.erico.minhasfinancas.entites.Usuario;

public interface UsuarioServices {
    
    abstract Usuario autenticar(String email, String senha);

    abstract Usuario salvarUsuario(Usuario usuario);
    
    abstract void validarEmail(String email);

    abstract Usuario obterPorId(Long id);

}
