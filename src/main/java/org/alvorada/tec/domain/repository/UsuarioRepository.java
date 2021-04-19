package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Criando uma query method para buscar o usuário pelo ID
    Optional<Usuario> findByLogin(String login);

}
