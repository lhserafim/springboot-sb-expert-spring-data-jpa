package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    // Injetando o repository na controller
    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("consulta-cliente-id/{id}")
    @ResponseBody // Também funcionou sem o ResponseBody
    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON
    // and passed back into the HttpResponse object.
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        // Optional é usada pois pode existir ou não um cliente por este id
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()) {
            // ResponseEntity.ok() retorna um 200 quando sucesso na API
            return ResponseEntity.ok(cliente.get()); // cliente.get() para obter o cliente que está dentro do Optional<>
        }
        // Retornando um 404
        return ResponseEntity.notFound().build();
    }
}
