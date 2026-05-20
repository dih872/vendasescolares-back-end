package com.vendasescolares.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // "receita" ou "despesa"

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}