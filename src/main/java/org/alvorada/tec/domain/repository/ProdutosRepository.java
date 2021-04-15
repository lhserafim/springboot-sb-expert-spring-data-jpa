package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {

}
