package com.arquiteturadesoftware.reservas.infrastructure.client;

import com.arquiteturadesoftware.reservas.business.dto.UsuarioDTO;
import com.arquiteturadesoftware.reservas.security.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "usuario-service",
        url = "http://localhost:8080/usuario",
        configuration = FeignClientConfig.class
)
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioDTO buscarUsuarioPorId(@PathVariable("id") Long id);
    @GetMapping
    UsuarioDTO buscaUsuarioPorLogin(@RequestParam("login") String login);
}