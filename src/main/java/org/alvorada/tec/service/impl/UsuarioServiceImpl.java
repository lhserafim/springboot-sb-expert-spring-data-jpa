package org.alvorada.tec.service.impl;

import org.alvorada.tec.domain.entity.Usuario;
import org.alvorada.tec.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder; // para encodar o password para poder validar na SecurityConfig

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario =  usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não cadastrado"));

        // Retornando as roles através de lógica. Na pratica não é assim
        // usando o isAdmin() que o Lombook implementa
        String[] roles = usuario.isAdmin() ?
                new String[]{"PRODUCAO", "USER"} : new String[]{"USER"};

        return User // User implements UserDetails, CredentialsContainer
                .builder()
                .username(usuario.getLogin())
                .username(usuario.getSenha()) // Não preciso do .password(encoder.encode("123")), pois a senha já esta criptografada na base
                .roles(roles)
                .build();
    }
//        if(!username.equals("cicrano")) {
//            throw new UsernameNotFoundException("Usuário não encontrado");
//        }
//
//        // Simulando a recuperação de usuário de uma base de dados
//        return User
//                .builder()
//                .username("cicrano")
//                .password(encoder.encode("123")) // Faz a criptografia da senha
//                .roles("USER", "PRODUCAO")
//                .build();
//    }
}
