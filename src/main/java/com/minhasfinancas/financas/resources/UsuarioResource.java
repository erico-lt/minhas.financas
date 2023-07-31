package com.minhasfinancas.financas.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.minhasfinancas.financas.entites.Usuario;
import com.minhasfinancas.financas.services.UsuarioServices;

public class UsuarioResource {
    
    @Autowired
    private UsuarioServices usuarioServices;

    @GetMapping
    public ResponseEntity <List<Usuario>> findAll() {

        return ResponseEntity.ok().body(usuarioServices.findAll());
    }
}
