package org.alvorada.tec;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) { // Aqui estou injetando a minha classe Clientes (de repository)
        return args -> {
            System.out.println("Adicionando Clientes");
            clientes.save(new Cliente("Luiz"));
            clientes.save(new Cliente("Thiago"));
            clientes.save(new Cliente("Daniela"));

            List<Cliente> todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);


            todosClientes.forEach(c -> {
                c.setNome(c.getNome() + " atualizado");
                clientes.save(c); // efetiva a atualização
            });
            System.out.println("Lista Atualizada");
            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            System.out.println("Pesquisar por nome");
            clientes.findByNomeLike("Thi").forEach(System.out::println);

            System.out.println("Excluir por cliente.id");
            clientes.findAll().forEach(c -> {
                clientes.delete(c);
            });

            todosClientes = clientes.findAll();
            todosClientes.forEach(System.out::println);

            if (todosClientes.isEmpty()) {
                System.out.println("Lista vazia");
            } else {
                todosClientes.forEach(System.out::println);
            }

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
