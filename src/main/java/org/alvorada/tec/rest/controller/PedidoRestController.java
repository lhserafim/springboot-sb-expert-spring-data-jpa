package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.ItemPedido;
import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.domain.enums.StatusPedido;
import org.alvorada.tec.rest.dto.AtualizacaoStatusPedidoDTO;
import org.alvorada.tec.rest.dto.InformacoesItemPedidoDTO;
import org.alvorada.tec.rest.dto.InformacoesPedidoDTO;
import org.alvorada.tec.rest.dto.PedidoDTO;
import org.alvorada.tec.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public Pedido salvarPedido(@RequestBody @Valid PedidoDTO dto) {
        Pedido pedido = pedidoService.salvar(dto);
        return pedido;
    }

    @PatchMapping("cancelar-pedido-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCancelarPedido(@PathVariable Integer id,
                                     @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String status = dto.getNovoStatus();
        pedidoService.atualizarStatus(id, StatusPedido.valueOf(status)); // Transformando string em ENUM

    }

    @GetMapping("consultar-pedido-id/{id}")
    public InformacoesPedidoDTO getPedidoById(@PathVariable Integer id) {
        return pedidoService
                .obterPedidoCompleto(id)
                // Se ele encontrar, retorna o .map()
                .map(ped -> converterPedidoBuilder(ped)) // Quando eu uso o map sem {} eu náo preciso retornar um objeto. Fica implicito
                // Se não, retorna o orElseThrow()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));

    }

    // Usando a estratégia do @Builder
    private InformacoesPedidoDTO converterPedidoBuilder(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatusPedido().name()) // Estou usando o name() para converter o enum em string
                .items(converterListaItens(pedido.getItens())) // converter os itens do pedido
                .build();
    }

    // Usando o builder
    private List<InformacoesItemPedidoDTO> converterListaItens(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList(); // Como não é uma boa prática retornar nulo, fazemos o tratamento para voltar vazio!
        }
        return itens.stream().map( i -> InformacoesItemPedidoDTO
                                    .builder()
                                    .quantidade(i.getQuantidade())
                                    .precoUnitario(i.getProduto().getPreco())
                                    .descricaoProduto(i.getProduto().getDescricao())
                                    .build()
        ).collect(Collectors.toList());

    }

    // Sem builder
    private InformacoesPedidoDTO converterPedido(Pedido pedido) {
        InformacoesPedidoDTO dto = new InformacoesPedidoDTO();
        dto.setCodigo(pedido.getId());
        dto.setNomeCliente(pedido.getCliente().getNome());
        dto.setCpf(pedido.getCliente().getCpf());
        dto.setDataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setTotal(pedido.getTotal());
        dto.setStatus(pedido.getStatusPedido().name());
        dto.setItems(pedido.getItens().stream().map( i -> {
                    InformacoesItemPedidoDTO itensDto = new InformacoesItemPedidoDTO();
                    itensDto.setQuantidade(i.getQuantidade());
                    itensDto.setPrecoUnitario(i.getProduto().getPreco());
                    itensDto.setDescricaoProduto(i.getProduto().getDescricao());
                    return itensDto;
                }).collect(Collectors.toList()));
        return dto;
    }

}

















