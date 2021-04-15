package org.alvorada.tec.rest.controller;

import org.alvorada.tec.service.PedidoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos-rest-controller")
public class PedidoRestController {

    private PedidoService pedidoService;

    public PedidoRestController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

}
