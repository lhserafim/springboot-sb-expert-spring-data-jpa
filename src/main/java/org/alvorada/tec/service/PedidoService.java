package org.alvorada.tec.service;

import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
