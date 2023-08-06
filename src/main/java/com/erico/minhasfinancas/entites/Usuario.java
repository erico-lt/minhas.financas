package com.erico.minhasfinancas.entites; 

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario", schema = "financas")
@Builder
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;    
                                                        
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @Column(name = "data_cadastro")
    private Instant dataCadastro;                

}
