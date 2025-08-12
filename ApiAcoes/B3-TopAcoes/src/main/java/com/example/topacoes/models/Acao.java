package com.example.topacoes.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "acoes")
@Data
public class Acao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoAtual;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal variacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = true)
    private Integer volumeNegociado;

    @PrePersist
    public void prePersist() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}