package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    
}
