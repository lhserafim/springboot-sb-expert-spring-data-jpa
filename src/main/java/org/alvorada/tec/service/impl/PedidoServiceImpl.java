package org.alvorada.tec.service.impl;

import lombok.RequiredArgsConstructor;
import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.entity.ItemPedido;
import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.domain.entity.Produto;
import org.alvorada.tec.domain.enums.StatusPedido;
import org.alvorada.tec.domain.repository.ClientesRepository;
import org.alvorada.tec.domain.repository.ItensPedidoRepository;
import org.alvorada.tec.domain.repository.PedidosRepository;
import org.alvorada.tec.domain.repository.ProdutosRepository;
import org.alvorada.tec.exception.RegrasNegocioException;
import org.alvorada.tec.rest.dto.ItemPedidoDto;
import org.alvorada.tec.rest.dto.PedidoDTO;
import org.alvorada.tec.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor // Para não precisar utilizar um construtor eu uso esta anotação do lombok
@Service
public class PedidoServiceImpl implements PedidoService {

    // precisa ser final para usar o @RequiredArgsConstructor
    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

    @Override
    @Transactional // Importante usar para garantir que só grave na base se TUDO estiver OK. Isso vai evitar pedido sem itens por exemplo
    public Pedido salvar(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setCliente(clientesRepository // Recuperando o cliente através do ID que vem no DTO
                .findById(dto.getCliente())
                .orElseThrow(() -> new RegrasNegocioException("ID do cliente não encontrado")));
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setStatusPedido(StatusPedido.REALIZADO);
        // Para poder setar os itens do pedido, preciso criar um método para percorrer a lista de itens que vem
        // do DTO e retornar uma lista pronta de itens já instanciados
        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItems());
        pedido.setItens(itensPedido);

        pedidosRepository.save(pedido); // salvo p/ gerar o ID
        itensPedidoRepository.saveAll(itensPedido); // Salvo os itens da lista

        return pedido;
    }

    // Método para percorrer a lista de itens que veio no DTO, pesquisar pelo ID do produto e recuperar o objeto
    // do produto para retornar uma lista de produtos pronta
    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDto> itens) {
        // Se a lista for vazia retornar erro
        if(itens.isEmpty()) {
            throw new RegrasNegocioException("O pedido não tem itens na lista");
        }
        // Montar a lista de pedido com base no DTO.
        // Transformo minha lista em uma stream, percorro utilizando o map, faço os sets e tranformo em uma lista novamente
        return itens.stream().map(dto -> {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(pedido); // Recupero o objeto recebido como parametro
                itemPedido.setProduto(produtosRepository // Busco o produto através da query no repository
                        .findById(dto.getProduto())
                        .orElseThrow(() -> new RegrasNegocioException("Produto inválido: " + dto.getProduto())));
                itemPedido.setQuantidade(dto.getQuantidade());
                return itemPedido; // map retorna um objeto
            }).collect(Collectors.toList()); // Transformo em lista novamente
    }

    // Uso Optional pq eu posso ter ou não o pedido
    // Repare que mesmo tendo um DTO para receber os dados do pedido, eu estou recebendo ele em uma entidade Pedido
    // Vou fazer a transfomação no controller de entidade para DTO. Náo é a pratica usada na SOMOS!
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }
}
