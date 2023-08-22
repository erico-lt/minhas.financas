package com.erico.minhasfinancas.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erico.minhasfinancas.entites.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{    
}
