package com.vendasescolares.repository;

import com.vendasescolares.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProdutoIdOrderByDataDesc(Long produtoId);

    @Query("SELECT AVG(f.nota) FROM Feedback f WHERE f.produto.id = :produtoId")
    Double mediaNotasPorProduto(@Param("produtoId") Long produtoId);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.produto.id = :produtoId")
    Long totalFeedbacksPorProduto(@Param("produtoId") Long produtoId);
}