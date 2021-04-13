package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
    
}
