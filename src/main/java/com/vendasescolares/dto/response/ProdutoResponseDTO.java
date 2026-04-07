package com.vendasescolares.dto.response;

import com.vendasescolares.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagem;
    private Boolean disponivel;
    private String categoria;
    private Double mediaAvaliacoes;
    private Long totalFeedbacks;

    public ProdutoResponseDTO(Produto produto, Double media, Long total) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.imagem = produto.getImagem();
        this.disponivel = produto.getDisponivel();
        this.categoria = produto.getCategoria();
        this.mediaAvaliacoes = media != null ? media : 0.0;
        this.totalFeedbacks = total != null ? total : 0;
    }
}