package com.arquiteturadesoftware.cadastrojogos.business.dto;

import com.arquiteturadesoftware.cadastrojogos.infrastructure.enums.TipoJogo;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private TipoJogo tipo;
    private Integer quantidadeDisponivel;
}