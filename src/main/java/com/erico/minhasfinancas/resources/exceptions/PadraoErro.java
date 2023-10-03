package com.erico.minhasfinancas.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PadraoErro implements Serializable{
    private static final long serialVersionUID = 1L;
        
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
