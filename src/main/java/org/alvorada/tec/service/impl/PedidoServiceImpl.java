package org.alvorada.tec.service.impl;

import org.alvorada.tec.domain.repository.PedidosRepository;
import org.alvorada.tec.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private PedidosRepository pedidosRepository;

    public PedidoServiceImpl(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }
}
