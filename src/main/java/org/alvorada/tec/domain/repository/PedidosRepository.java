package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("SELECT p " +
            " FROM Pedido p " +
            " LEFT JOIN FETCH p.itens " +
            "WHERE p.id = :id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);

}
