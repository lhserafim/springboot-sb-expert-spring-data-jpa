package org.alvorada.tec;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.entity.Pedido;
import org.alvorada.tec.domain.repository.ClientesRepository;
import org.alvorada.tec.domain.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//@SpringBootApplication
public class VendasApplicationCommandLIneRunner {

    @Bean
    public CommandLineRunner init(@Autowired ClientesRepository clientesRepository,
                                  @Autowired PedidosRepository pedidosRepository) { // Aqui estou injetando a minha classe Clientes (de repository)
        return args -> {
            System.out.println("Adicionando Clientes");
            clientesRepository.save(new Cliente("Luiz"));
            clientesRepository.save(new Cliente("Thiago"));
            clientesRepository.save(new Cliente("Daniela"));

            boolean existe = clientesRepository.existsByNome("Daniela");
            System.out.println("Existe um cliente com nome Daniela " + existe);

            List<Cliente> todosClientes = clientesRepository.findAll();
            todosClientes.forEach(System.out::println);

            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clientesRepository.save(c); // efetiva a atualização
            });
            System.out.println("Lista Atualizada");
            todosClientes = clientesRepository.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Pesquisar por nome");
            clientesRepository.findByNomeLike("Thi").forEach(System.out::println);

            System.out.println("Pesquisar por nome 2");
            clientesRepository.encontrarPorNome("Dani").forEach(System.out::println);

            System.out.println("Excluir por cliente.id");
            clientesRepository.findAll().forEach(clientesRepository::delete);

            todosClientes = clientesRepository.findAll();
            todosClientes.forEach(System.out::println);

            if (todosClientes.isEmpty()) {
                System.out.println("Lista vazia");
            } else {
                todosClientes.forEach(System.out::println);
            }

            System.out.println("Criando um pedido para o cliente fulano");
            // Criar o cliente e salvar
            Cliente fulano = new Cliente("Fulano");
            clientesRepository.save(fulano);

            // Criar o pedido
            Pedido p = new Pedido();
            p.setCliente(fulano); // Adicionando o cliente ao pedido
            p.setDataPedido(LocalDate.now()); // Usando a nova api do java 8 p/ pegar a data
            p.setTotal(BigDecimal.valueOf(100)); // fazendo o wrap
            pedidosRepository.save(p);

            // Recuperar os pedidos do cliente
            Cliente cliente = clientesRepository.findClienteFetchPedidos(fulano.getId()); // Pesquisando os pedidos pelo id do cliente
            // posso colocar em cliente, pq clientes tem a propriedade de lista de pedidos
            System.out.println(cliente);
            System.out.println(cliente.getPedidos());

            // Outra forma de recuperar os pedidos. Usando o query method
            pedidosRepository.findByCliente(fulano).forEach(System.out::println);

            System.out.println(LocalDateTime.now());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplicationCommandLIneRunner.class, args);
    }
}
