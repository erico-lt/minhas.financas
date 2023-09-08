package com.erico.minhasfinancas.repositorys;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erico.minhasfinancas.entites.Lancamento;
import com.erico.minhasfinancas.enums.EnumTipo;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
    
    @Query(value = "select sum(l.valor) from Lancamento l join l.usuario u where " + 
                    "u.id = :idUsuario and l.tipo = :tipo group by u")
    BigDecimal oberSaldoPorLancamentoEUsuario(@Param(value = "idUsuario") Long idUsuario, @Param(value = "tipo") EnumTipo tipo);
}
