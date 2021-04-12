package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// O JpaRepository recebe dois tipos parametrizados. A entidade e o tipo de dado do ID da entidade
public interface Clientes extends JpaRepository<Cliente, Integer> {

    // O nome findByNomeLike não é "aleatório", é uma convenção que o JpaRepository lê e entende que é uma pesquisa com like
    List<Cliente> findByNomeLike(String nome);
}
