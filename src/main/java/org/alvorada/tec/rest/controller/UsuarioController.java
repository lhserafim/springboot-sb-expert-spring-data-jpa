package org.alvorada.tec.rest.controller;

import lombok.RequiredArgsConstructor;
import org.alvorada.tec.domain.entity.Usuario;
import org.alvorada.tec.service.impl.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios-rest-controller")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("salvar-usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha()); // Criptografar a senha
        usuario.setSenha(senhaCriptografada); // setar a senha no objeto
        return usuarioService.salvar(usuario);
    }
}
