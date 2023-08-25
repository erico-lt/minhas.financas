package com.erico.minhasfinancas.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.services.impl.LancamentoServicesImpl;

@RestController
@RequestMapping(value = "/api/lancamentos")
public class LancamentoResource {
    
    @Autowired
    LancamentoServicesImpl lancamentoServices;

    @PostMapping
    public ResponseEntity<Lancamento> salvar(@RequestBody Lancamento lancamento) {

        return ResponseEntity.ok().body(lancamentoServices.salvar(lancamento));
    }

    @PutMapping
    public ResponseEntity<Lancamento> atualizar(@RequestBody Lancamento lancamento) {

        return ResponseEntity.ok().body(lancamentoServices.atualizar(lancamento));
    }

    @DeleteMapping
    public ResponseEntity<Void>  deletar(Lancamento lancamento) {

        lancamentoServices.deletar(lancamento);
        return ResponseEntity.noContent().build();
    }    

    @GetMapping
    public ResponseEntity<List<Lancamento>> buscar(Lancamento lancamentoFiltro) {

        return ResponseEntity.ok().body(lancamentoServices.buscar(lancamentoFiltro));
    }

    @GetMapping
    public ResponseEntity<Void> atualizaStatus(@RequestBody Lancamento lancamento, EnumStatus status) {

        lancamentoServices.atualizarStatus(lancamento, status);
        return ResponseEntity.noContent().build();
    }
}
