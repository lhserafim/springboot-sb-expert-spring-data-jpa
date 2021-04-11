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
            /* Mais verboso...
            Cliente cliente = new Cliente(); // Aqui estou criando uma nova instancia de Cliente (de entity)
            cliente.setNome("Luiz");
            clientes.salvar(cliente);
            */
            // Menos verboso
            clientes.salvar(new Cliente("Luiz"));
            clientes.salvar(new Cliente("Thiago"));
            clientes.salvar(new Cliente("Daniela"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
