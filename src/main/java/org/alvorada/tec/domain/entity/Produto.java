package org.alvorada.tec.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "O campo descrição é obrigatório")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "O campo preço é obrigatório")
    private BigDecimal preco;

}
