package org.alvorada.tec.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.alvorada.tec.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {
    @NotNull(message = "Informe o código do cliente") // ou @NotEmpty
    private Integer cliente;
    @NotNull(message = "Informe o valor total do pedido")
    private BigDecimal total;
    @NotEmptyList(message = "Pedido não pode ser criado sem itens")
    private List<ItemPedidoDto> items;

}
