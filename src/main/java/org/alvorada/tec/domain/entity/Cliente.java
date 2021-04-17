package org.alvorada.tec.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // O h2 utiliza esta regra p/ controlar a sequence
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "O campo nome é obrigatório") // Anotação usada pelo Bean Validation
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "O campo CPF é obrigatório")
    @CPF(message = "O CPF informado é inválido") // É uma validação de CPF que já existe p/ o local BR
    private String cpf;


    // OPCIONAL para este contexto!
    // Caso eu queira trazer todos os pedidos de um cliente, eu posso adicionar o mapeamento abaixo
    // Usei Set, mas poderia usar List ou Collection. O Set garante que não terei pedidos repetidos na lista + indicado
    // O mappedBy deve referenciar a propriedade que eu tenho em Pedido que é a FK de cliente. Ex: private Cliente cliente;
    @JsonIgnore // Para que o meu JSON não retorne a lista de pedidos
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    // Os construtores foram criados p/ que eu possa rodar jdbcTemplate.query

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
