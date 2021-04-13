package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
