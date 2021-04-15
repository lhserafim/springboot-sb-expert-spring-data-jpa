package org.alvorada.tec.rest.dto;

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
public class ItemPedidoDto {
    private Integer produto;
    private Integer quantidade;

}
