package com.arquiteturadesoftware.cadastrojogos.infrastructure.security;

import com.arquiteturadesoftware.cadastrojogos.business.dto.UsuarioDTO;
import com.arquiteturadesoftware.cadastrojogos.infrastructure.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Nota: Este método teria dificuldade de passar o Token para o Feign
        // pois a interface padrão não prevê o parâmetro Token.
        throw new UsernameNotFoundException("Use o método carregaDadosUsuario");
    }

    public UserDetails carregaDadosUsuario(String login, String token){
        UsuarioDTO usuarioDTO = client.buscaUsuarioPorLogin(login, token);
        return User
                .withUsername(login)
                .password(usuarioDTO.getSenha())
                .authorities(new ArrayList<>())
                .build();
    }
}
