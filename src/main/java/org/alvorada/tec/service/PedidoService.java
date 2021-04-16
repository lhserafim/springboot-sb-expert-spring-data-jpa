package org.alvorada.tec.service;

import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
}
