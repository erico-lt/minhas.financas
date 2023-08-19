package com.erico.minhasfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.services.impl.UsuarioServicesImpl;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
    
    @Autowired
    private UsuarioServicesImpl usuarioServicesImpl;

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        
        return ResponseEntity.ok().body(usuarioServicesImpl.salvarUsuario(usuario)); 
    }
   
} 
