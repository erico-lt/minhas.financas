package com.minhasfinancas.financas.entites;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "usuario")
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;    
                                                        
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String senha;

    @Column(name = "data_cadastro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    private Instant dataCadastro;         
    
    @OneToMany(mappedBy = "usuario")    
    @Setter(lombok.AccessLevel.NONE)
    private Set<Lancamento> lancamento =  new HashSet<>();               

}
