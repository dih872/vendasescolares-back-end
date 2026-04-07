package com.vendasescolares.dto.response;

import com.vendasescolares.model.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private Long id;
    private String usuarioNome;
    private String produtoNome;
    private Integer nota;
    private String comentario;
    private String data;

    public FeedbackResponseDTO(Feedback feedback) {
        this.id = feedback.getId();
        this.usuarioNome = feedback.getUsuario() != null ? feedback.getUsuario().getNome() : "Anônimo";
        this.produtoNome = feedback.getProduto().getNome();
        this.nota = feedback.getNota();
        this.comentario = feedback.getComentario();
        this.data = feedback.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}