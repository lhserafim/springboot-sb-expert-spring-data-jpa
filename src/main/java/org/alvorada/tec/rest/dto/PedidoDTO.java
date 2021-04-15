package org.alvorada.tec.rest.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Criando o DTO (Data Transfer Object) para o JSON abaixo
 * {
 *   "cliente": 1,
 *   "total": 100,
 *   "items": [
 *     {
 *       "produto": 1,
 *       "quantidade": 10
 *     }
 *   ]
 * }
 * */

public class PedidoDTO {
    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDto> items;

}
