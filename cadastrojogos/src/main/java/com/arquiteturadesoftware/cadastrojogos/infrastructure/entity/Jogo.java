package com.arquiteturadesoftware.cadastrojogos.infrastructure.entity;

import com.arquiteturadesoftware.cadastrojogos.infrastructure.enums.TipoJogo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jogos")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoJogo tipo;
    private Integer quantidadeDisponivel;
    private Integer quantidadeTotal;
}
