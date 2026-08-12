package com.arquiteturadesoftware.cadastrojogos.business.service;

import com.arquiteturadesoftware.cadastrojogos.business.converter.JogoConverter;
import com.arquiteturadesoftware.cadastrojogos.business.dto.JogoDTO;
import com.arquiteturadesoftware.cadastrojogos.infrastructure.entity.Jogo;
import com.arquiteturadesoftware.cadastrojogos.infrastructure.repository.JogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JogoService {
    private final JogoRepository jogoRepository;
    private final JogoConverter jogoConverter;

    public List<JogoDTO> listarJogosVitrine() {
        return jogoRepository.findByQuantidadeDisponivelGreaterThan(0)
                .stream()
                .map(jogoConverter::paraDTO)
                .toList();
    }

    public JogoDTO salvarJogo(JogoDTO dto) {
        if (jogoRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RuntimeException("Já existe um jogo cadastrado com este nome: " + dto.getNome());
        }
        Jogo jogo = jogoConverter.paraEntity(dto);
        return jogoConverter.paraDTO(jogoRepository.save(jogo));
    }

    public JogoDTO atualizarJogo(Long id, JogoDTO dto) {
        Jogo jogoExistente = jogoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + id));
        Jogo jogoAtualizado = jogoConverter.atualizarJogo(dto, jogoExistente);
        return jogoConverter.paraDTO(jogoRepository.save(jogoAtualizado));
    }

    public void deletaJogoPorId(Long id) {
        if (!jogoRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir: Jogo não encontrado com o ID: " + id);
        }
        jogoRepository.deleteById(id);
    }

    public JogoDTO buscarJogoPorId(Long id) {
        return jogoRepository.findById(id)
                .map(jogoConverter::paraDTO)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + id));
    }

    //Decrementa o estoque disponível do jogo na prateleira
    @Transactional
    public void decrementarEstoque(Long id) {
        Jogo jogo = jogoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + id));

        // Impede que a quantidade disponível fique negativa
        if (jogo.getQuantidadeDisponivel() == null || jogo.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Não há unidades disponíveis para o jogo: " + jogo.getNome());
        }

        jogo.setQuantidadeDisponivel(jogo.getQuantidadeDisponivel() - 1);
        jogoRepository.save(jogo);
    }
}