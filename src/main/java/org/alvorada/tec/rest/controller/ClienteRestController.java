package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clientes-rest-controller")
public class ClienteRestController {

    // Injetando o repository na controller
    private Clientes clientes;

    public ClienteRestController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("consulta-cliente-id/{id}") // Refatorando sem ResponseEntity
    // Não preciso do @ResponseStatus, pois quando não tem nada informado, por padrão já volta 200
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientes
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    // Com o POST abaixo não preciso mais da CommandLineRunner p/ criar um cliente
    @PostMapping("salvar-cliente")
    @ResponseStatus(HttpStatus.CREATED) // Retorna o código 201 - created
    public Cliente saveCliente(@RequestBody Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("deletar-cliente-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClienteById(@PathVariable Integer id) {
        clientes.findById(id)
                .map(c -> {
                    clientes.delete(c);
                    return c; // O método map precisa retornar um objeto
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("/atualizar-cliente-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClienteById( @PathVariable Integer id,
                                   @RequestBody Cliente cliente) {
        clientes.findById(id)
                .map(c -> {
                    cliente.setId(c.getId()); // Ao invés de usar o set de cada atributo da classe, eu uso o setId e automaticamente atualizo todas as propriedades
                    clientes.save(cliente);
                    return c;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping("consulta-cliente-filtro")
    public List<Cliente> findByFiltro(Cliente filtro) { // Neste caso não pode ter o RequestBody. Se não retorna bad request
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }
}
