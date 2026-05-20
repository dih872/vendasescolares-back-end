package com.vendasescolares.controller.admin;

import com.vendasescolares.model.Lancamento;
import com.vendasescolares.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/lancamentos")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoRepository lancamentoRepository;

    @GetMapping
    public ResponseEntity<List<Lancamento>> listar() {
        return ResponseEntity.ok(lancamentoRepository.findAllByOrderByCreatedAtDesc());
    }

    @GetMapping("/totais")
    public ResponseEntity<Map<String, BigDecimal>> totais() {
        BigDecimal receitas = lancamentoRepository.somarPorTipo("receita");
        BigDecimal despesas = lancamentoRepository.somarPorTipo("despesa");
        BigDecimal lucro = receitas.subtract(despesas);

        Map<String, BigDecimal> totais = new HashMap<>();
        totais.put("receitas", receitas);
        totais.put("despesas", despesas);
        totais.put("lucro", lucro);

        return ResponseEntity.ok(totais);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@RequestBody Lancamento lancamento) {
        return ResponseEntity.ok(lancamentoRepository.save(lancamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        lancamentoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}