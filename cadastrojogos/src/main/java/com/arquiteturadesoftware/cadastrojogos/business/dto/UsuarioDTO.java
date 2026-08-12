package com.arquiteturadesoftware.cadastrojogos.business.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String cpf;
    private String email;
    private String senha;

}
