package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
