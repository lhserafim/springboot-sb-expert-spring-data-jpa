package org.alvorada.tec.rest.controller;

import lombok.RequiredArgsConstructor;
import org.alvorada.tec.domain.entity.Usuario;
import org.alvorada.tec.exception.SenhaInvalidaException;
import org.alvorada.tec.rest.dto.CredenciaisDTO;
import org.alvorada.tec.rest.dto.TokenDTO;
import org.alvorada.tec.security.jwt.JwtService;
import org.alvorada.tec.service.impl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios-rest-controller")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("salvar-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha()); // Criptografar a senha
        usuario.setSenha(senhaCriptografada); // setar a senha no objeto
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
