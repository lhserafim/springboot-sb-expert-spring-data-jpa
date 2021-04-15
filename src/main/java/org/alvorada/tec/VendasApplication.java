package org.alvorada.tec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VendasApplication {

    // Foi criado um método Post p/ criar os clientes e por isso posso desligar o método abaixo
//    @Bean
//    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes) {
//        return args -> {
//            Cliente c = new Cliente("Cicrano");
//            clientes.save(c);
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
