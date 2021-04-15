package org.alvorada.tec.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemPedidoDto {
    private Integer produto;
    private Integer quantidade;

}
