package com.erico.minhasfinancas.entites; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Entity
@Table(name = "usuario", schema = "financas")
@Builder
public class Usuario implements Serializable{
    private static final long serialVersionUID = 1L;    
                                                        
    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY) 
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @NonNull
    @JsonIgnore
    private String senha;     
   
    @OneToMany(mappedBy = "usuario")
    @Builder.Default
    @JsonIgnore
    private List<Lancamento> lancamento = new ArrayList<>();

}
