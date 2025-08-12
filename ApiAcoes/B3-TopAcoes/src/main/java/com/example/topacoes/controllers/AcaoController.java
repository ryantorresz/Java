package com.example.topacoes.controllers;

import com.example.topacoes.models.Acao;
import com.example.topacoes.services.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acoes")
public class AcaoController {

    private final AcaoService acaoService;

    @Autowired
    public AcaoController(AcaoService acaoService) {
        this.acaoService = acaoService;
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Acao>> getTop10Acoes() {
        return ResponseEntity.ok(acaoService.buscarTop10AcoesBrasileiras());
    }

    @PostMapping("/atualizar")
    public ResponseEntity<String> atualizarAcoes() {
        acaoService.atualizarAcoesPeriodicamente();
        return ResponseEntity.ok("Ações atualizadas com sucesso!");
    }

    @GetMapping("/historico")
    public ResponseEntity<List<Acao>> getHistoricoAcoes() {
        return ResponseEntity.ok(acaoService.buscarTop10AcoesBrasileiras());
    }
}