package com.vendasescolares.controller.admin;

import com.vendasescolares.model.Lancamento;
import com.vendasescolares.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/lancamentos")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://vendasescolares.vercel.app"
})
@RequiredArgsConstructor
public class LancamentoController {

    private static final ZoneId FUSO = ZoneId.of("America/Recife");

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
        lancamento.setId(null);
        lancamento.setCreatedAt(ZonedDateTime.now(FUSO).toLocalDateTime());
        return ResponseEntity.ok(lancamentoRepository.save(lancamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (lancamentoRepository.existsById(id)) {
            lancamentoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}