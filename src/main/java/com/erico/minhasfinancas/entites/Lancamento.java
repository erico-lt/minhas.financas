package com.erico.minhasfinancas.entites;

import java.io.Serializable;
import java.time.Instant;


import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.erico.minhasfinancas.enums.EnumStatus;
import com.erico.minhasfinancas.enums.EnumTipo;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "lancamento", schema = "financas")
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;    

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private int mes;
    private int ano;  
    private Double valor;

    @Enumerated(value = EnumType.STRING)
    private EnumTipo tipo;

    @Enumerated(value = EnumType.STRING)
    private EnumStatus status;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Convert(converter = Jsr310JpaConverters.InstantConverter.class)
    private Instant data_cadastro;       
}
