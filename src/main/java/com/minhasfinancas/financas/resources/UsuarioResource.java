package com.minhasfinancas.financas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhasfinancas.financas.services.impl.UsuarioServicesImpl;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
    
    @Autowired
    private UsuarioServicesImpl usuarioServicesImpl;


} 
