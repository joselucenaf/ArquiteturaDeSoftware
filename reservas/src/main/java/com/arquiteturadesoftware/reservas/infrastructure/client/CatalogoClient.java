package com.arquiteturadesoftware.reservas.infrastructure.client;

import com.arquiteturadesoftware.reservas.business.dto.JogoDTO;
import com.arquiteturadesoftware.reservas.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "catalogo-jogos",
        url = "${catalogo_jogos.url}",
        configuration = FeignClientConfig.class
)
public interface CatalogoClient {

    @GetMapping("/jogos/{id}")
    JogoDTO buscarJogoPorId(@PathVariable("id") Long id);

    //endpoint de decremento do Catálogo
    @PatchMapping("/jogos/{id}/decrementar-estoque")
    void decrementarEstoque(@PathVariable("id") Long id);
}