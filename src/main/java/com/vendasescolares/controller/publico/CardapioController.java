package com.vendasescolares.controller.publico;

import com.vendasescolares.dto.response.ProdutoResponseDTO;
import com.vendasescolares.model.Produto;
import com.vendasescolares.repository.FeedbackRepository;
import com.vendasescolares.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cardapio")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CardapioController {

    private final ProdutoRepository produtoRepository;
    private final FeedbackRepository feedbackRepository;

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarCardapio() {
        List<Produto> produtos = produtoRepository.findByDisponivelTrue();

        List<ProdutoResponseDTO> response = produtos.stream()
                .map(produto -> {
                    Double media = feedbackRepository.mediaNotasPorProduto(produto.getId());
                    Long total = feedbackRepository.totalFeedbacksPorProduto(produto.getId());
                    return new ProdutoResponseDTO(produto, media, total);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorCategoria(@PathVariable String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoria(categoria);

        List<ProdutoResponseDTO> response = produtos.stream()
                .filter(Produto::getDisponivel)
                .map(produto -> {
                    Double media = feedbackRepository.mediaNotasPorProduto(produto.getId());
                    Long total = feedbackRepository.totalFeedbacksPorProduto(produto.getId());
                    return new ProdutoResponseDTO(produto, media, total);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}