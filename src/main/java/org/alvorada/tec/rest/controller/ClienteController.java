package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    // Com o POST abaixo não preciso mais da CommandLineRunner p/ criar um cliente
    @PostMapping("salvar-cliente")
    @ResponseBody
    // Como estou recebendo um objeto Json p/ representar cliente, preciso colocar a anotação @RequestBody
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("deletar-cliente-id/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> deleteClienteById(@PathVariable Integer id) {
        // Optional é usada pois pode existir ou não um cliente por este id
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()) {
            clientes.delete(cliente.get()); // Dando um get() no Optional<> para pegar o cliente retornado e excluí-lo
            // ResponseEntity.noContent() é um retorno 204 de sucesso mas que não retorna nenhum conteúdo
            return ResponseEntity.noContent().build();
        }
        // Retornando um 404
        return ResponseEntity.notFound().build();
    }
}
