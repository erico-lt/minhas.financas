package com.erico.minhasfinancas.resources;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erico.minhasfinancas.dto.UsuarioDTO;
import com.erico.minhasfinancas.entites.Usuario;
import com.erico.minhasfinancas.services.impl.LancamentoServicesImpl;
import com.erico.minhasfinancas.services.impl.UsuarioServicesImpl;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioResource {
    
    @Autowired
    private UsuarioServicesImpl usuarioServicesImpl;

    @Autowired
    private LancamentoServicesImpl lancamentoServicesImpl;

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody UsuarioDTO dto) {
        
        Usuario user = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();   
        return ResponseEntity.ok().body(usuarioServicesImpl.salvarUsuario(user)); 
    }

    @GetMapping(value = "/autenticar")
    public ResponseEntity<Usuario> autenticar(@RequestBody UsuarioDTO dto) {

        return ResponseEntity.ok().body(usuarioServicesImpl.autenticar(dto.getEmail(), dto.getSenha()));
    }   

    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> obterSaldoUsuario(@PathVariable Long id) {
        
        usuarioServicesImpl.obterPorId(id);
        return ResponseEntity.ok(lancamentoServicesImpl.obterSaldoPorUsuario(id));
    }
} 
