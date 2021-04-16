package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.rest.dto.PedidoDTO;
import org.alvorada.tec.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos-rest-controller")
public class PedidoRestController {

    private PedidoService pedidoService;

    public PedidoRestController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Criar pedido. Retorna o ID do pedido
    @PostMapping("salvar-pedido")
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido salvarPedido(@RequestBody PedidoDTO dto) {
        Pedido pedido = pedidoService.salvar(dto);
        return pedido;
    }

}
