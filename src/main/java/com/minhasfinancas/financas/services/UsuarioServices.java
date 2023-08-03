package com.minhasfinancas.financas.services;

import com.minhasfinancas.financas.entites.Usuario;


public interface UsuarioServices {
    
    abstract Usuario autenticar(String email, String senha);

    abstract Usuario salvarUsuario(Usuario usuario);
    
    abstract void validarEmail(String email);

}
