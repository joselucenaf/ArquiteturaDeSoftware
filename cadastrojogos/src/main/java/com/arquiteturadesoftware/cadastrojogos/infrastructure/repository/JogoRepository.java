package com.arquiteturadesoftware.cadastrojogos.infrastructure.repository;

import com.arquiteturadesoftware.cadastrojogos.infrastructure.entity.Jogo;
import com.arquiteturadesoftware.cadastrojogos.infrastructure.enums.TipoJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    List<Jogo> findByQuantidadeDisponivelGreaterThan(Integer quantidade);

    boolean existsByNomeIgnoreCase(String nome);
    List<Jogo> findByTipo(TipoJogo tipo);
}