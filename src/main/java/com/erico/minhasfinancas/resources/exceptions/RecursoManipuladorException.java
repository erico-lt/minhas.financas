package com.erico.minhasfinancas.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.erico.minhasfinancas.services.impl.exceptions.RecursoNaoEncontradoException;
import com.erico.minhasfinancas.services.impl.exceptions.RegraNegocioException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RecursoManipuladorException {
    
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<PadraoErro> recursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest request) {
        String error = "Recurso nao encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;

        PadraoErro err = new PadraoErro(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<PadraoErro> divergenciaDeDados(RegraNegocioException e, HttpServletRequest request) {
        
        String error = "Divergencia de  dados";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        
        PadraoErro err = new PadraoErro(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
