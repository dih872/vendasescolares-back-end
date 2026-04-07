package com.vendasescolares.controller.cliente;

import com.vendasescolares.dto.response.FeedbackResponseDTO;
import com.vendasescolares.dto.response.MensagemResponse;
import com.vendasescolares.model.Feedback;
import com.vendasescolares.model.Produto;
import com.vendasescolares.repository.FeedbackRepository;
import com.vendasescolares.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ClienteFeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<FeedbackResponseDTO>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(
                feedbackRepository.findByProdutoIdOrderByDataDesc(produtoId).stream()
                        .map(FeedbackResponseDTO::new)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Feedback feedbackRequest) {
        try {
            Produto produto = produtoRepository.findById(feedbackRequest.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            Feedback feedback = new Feedback();
            feedback.setProduto(produto);
            feedback.setNota(feedbackRequest.getNota());
            feedback.setComentario(feedbackRequest.getComentario());
            feedback.setData(LocalDateTime.now());

            return ResponseEntity.ok(feedbackRepository.save(feedback));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MensagemResponse("Erro ao criar feedback: " + e.getMessage()));
        }
    }
}