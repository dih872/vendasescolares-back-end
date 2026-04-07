package com.vendasescolares.controller.admin;

import com.vendasescolares.dto.response.ProdutoResponseDTO;
import com.vendasescolares.model.Produto;
import com.vendasescolares.repository.FeedbackRepository;
import com.vendasescolares.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/produtos")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AdminProdutoController {

    private final ProdutoRepository produtoRepository;
    private final FeedbackRepository feedbackRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoResponseDTO> response = produtos.stream()
                .map(produto -> {
                    Double media = feedbackRepository.mediaNotasPorProduto(produto.getId());
                    Long total = feedbackRepository.totalFeedbacksPorProduto(produto.getId());
                    return new ProdutoResponseDTO(produto, media, total);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        return produtoRepository.findById(id)
                .map(p -> {
                    produto.setId(id);
                    return ResponseEntity.ok(produtoRepository.save(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}