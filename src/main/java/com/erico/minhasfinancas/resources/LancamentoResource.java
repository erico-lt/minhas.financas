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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erico.minhasfinancas.dto.EnumStatusDTO;
import com.erico.minhasfinancas.dto.LancamentoDTO;
import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;
import com.erico.minhasfinancas.services.LancamentoServices;
import com.erico.minhasfinancas.services.UsuarioServices;

@RestController
@RequestMapping(value = "/api/lancamentos")
public class LancamentoResource {

    @Autowired
    LancamentoServices lancamentoServices;

    @Autowired
    UsuarioServices usuarioServices;

    // Metodo para salvar Lancamento
    @PostMapping
    public ResponseEntity<Lancamento> salvar(@RequestBody LancamentoDTO dto) {

        Lancamento lancamento = new Lancamento();
        converterLancamentoDtoParaLancamento(lancamento, dto);
        return ResponseEntity.ok().body(lancamentoServices.salvar(lancamento));
    }

    // Metodo para atualizar Lancamento
    @PutMapping(value = "/{id}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long id, @RequestBody Lancamento lancamento) {
        
        return ResponseEntity.ok().body(lancamentoServices.atualizar(lancamento));
    }

    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        lancamentoServices.deletar(lancamentoServices.obterPorId(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Lancamento>> buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "tipo", required = false) EnumTipo tipo,
            @RequestParam(value = "id_usuario", required = true) Long idUsuario) {

        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        lancamentoFiltro.setUsuario(usuarioServices.obterPorId(idUsuario));

        return ResponseEntity.ok().body(lancamentoServices.buscar(lancamentoFiltro));
    }     

    @PutMapping("/{id}/atualizar-status")
    public ResponseEntity<Lancamento> atualizaStatus(@PathVariable Long id, @RequestBody EnumStatusDTO dto) {

        Lancamento lanc = lancamentoServices.obterPorId(id);
        lancamentoServices.atualizarStatus(lanc, EnumStatus.valueOf(dto.getStatus()));
        return ResponseEntity.ok().body(lanc);
    }

    public void converterLancamentoDtoParaLancamento(Lancamento lancamento, LancamentoDTO dto) {

        lancamento.setDescricao(dto.getDescricao());
        lancamento.setMes(dto.getMes());
        lancamento.setAno(dto.getAno());
        lancamento.setValor(dto.getValor());
        lancamento.setTipo(EnumTipo.valueOf(dto.getTipo()));
        lancamento.setStatus(EnumStatus.valueOf(dto.getStatus()));
        lancamento.setUsuario(usuarioServices.obterPorId(dto.getUsuario()));
    }

}
