package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// O JpaRepository recebe dois tipos parametrizados. A entidade e o tipo de dado do ID da entidade
public interface Clientes extends JpaRepository<Cliente, Integer> {

    // Query Methods

    // O nome findByNomeLike não é "aleatório", é uma convenção que o JpaRepository lê e entende que é uma pesquisa
    // com like em cima do atributo Nome, que existe na minha classe Cliente
    List<Cliente> findByNomeLike(String nome);

    // Outros exemplos de query methods
    List<Cliente> findByNomeOrId(String nome, Integer id); // Os parâmetros precisam ser passados na ordem que são declarados no nome do método
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);

    // Este exemplo é um método e não um query method!
    boolean existsByNome(String nome);

    // @Query
    @Query(value = "SELECT * FROM cliente WHERE nome LIKE '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(" DELETE FROM Cliente c WHERE c.nome = :nome")
    @Modifying
    void deletarPorNome(@Param("nome") String nome);

    // Fazendo consultas com entidades relacionadas via JPA
    // Veja que eu só coloco FETCH e o c.pedidos (que é como eu mapeei na entidade Cliente: private Set<Pedido> pedidos;)
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos WHERE c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

}
