package org.alvorada.tec.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder; // para encodar o password para poder validar na SecurityConfig

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("cicrano")) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        // Simulando a recuperação de usuário de uma base de dados
        return User
                .builder()
                .username("cicrano")
                .password(encoder.encode("123")) // Faz a criptografia da senha
                .roles("USER", "PRODUCAO")
                .build();
    }
}
