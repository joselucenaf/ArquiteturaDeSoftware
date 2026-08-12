package com.arquiteturadesoftware.cadastrojogos.business.converter;

import com.arquiteturadesoftware.cadastrojogos.business.dto.JogoDTO;
import com.arquiteturadesoftware.cadastrojogos.infrastructure.entity.Jogo;
import org.springframework.stereotype.Component;

@Component
public class JogoConverter {

    public JogoDTO paraDTO(Jogo entity) {
        return JogoDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .tipo(entity.getTipo())
                .quantidadeDisponivel(entity.getQuantidadeDisponivel())
                .build();
    }

    public Jogo paraEntity(JogoDTO dto) {
        return Jogo.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .tipo(dto.getTipo())
                .quantidadeDisponivel(dto.getQuantidadeDisponivel())
                .quantidadeTotal(dto.getQuantidadeDisponivel())
                .build();
    }

    public Jogo atualizarJogo(JogoDTO dto, Jogo entity) {
        return Jogo.builder()
                .id(entity.getId())
                .nome(dto.getNome() != null ? dto.getNome() : entity.getNome())
                .descricao(dto.getDescricao() != null ? dto.getDescricao() : entity.getDescricao())
                .tipo(dto.getTipo() != null ? dto.getTipo() : entity.getTipo())
                .quantidadeDisponivel(dto.getQuantidadeDisponivel() != null ?
                        dto.getQuantidadeDisponivel() : entity.getQuantidadeDisponivel())
                .quantidadeTotal(entity.getQuantidadeTotal())
                .build();
    }


}
