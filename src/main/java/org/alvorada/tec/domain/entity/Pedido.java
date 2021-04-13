package org.alvorada.tec.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    // Mapeamento do relacionamento de FK. Um cliente tem muitos pedidos
    @ManyToOne
    @JoinColumn(name = "cliente_id") // colocar o nome da coluna de FK da tabela de PEDIDO e não da tabela de CLIENTE
    private Cliente cliente; // Repare que aqui estou usando uma classe para definir o tipo e não o id

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    // Fazendo o mapeamento de um BigDecimal, com precisão de 2
    @Column(name = "total", length = 20, precision = 2)
    private BigDecimal total;

    // Note que só consigo criar este mapeamento após fazer o mapeamento da entidade many. Pois preciso criar o
    // nome que será usado no mappedBy
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens; // Usei o list só para ficar didático. Poderia ser o Set ou Collection

    public List<ItemPedido> getItens() {
        return itens;
    }

    // Não criei o Set apenas o getItens

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
