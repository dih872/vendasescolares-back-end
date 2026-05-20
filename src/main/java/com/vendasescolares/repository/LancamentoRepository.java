package com.vendasescolares.repository;

import com.vendasescolares.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findAllByOrderByCreatedAtDesc();

    List<Lancamento> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(l.valor), 0) FROM Lancamento l WHERE l.tipo = :tipo AND l.createdAt BETWEEN :inicio AND :fim")
    BigDecimal somarPorTipoEPeriodo(String tipo, LocalDateTime inicio, LocalDateTime fim);
}