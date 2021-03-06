package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Cliente;
import org.alvorada.tec.domain.repository.ClientesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    // Injetando o repository na controller
    private ClientesRepository clientesRepository;

    public ClienteController(ClientesRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping("consulta-cliente-id/{id}")
    @ResponseBody // Também funcionou sem o ResponseBody
    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON
    // and passed back into the HttpResponse object.
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        // Optional é usada pois pode existir ou não um cliente por este id
        Optional<Cliente> cliente = clientesRepository.findById(id);
        if(cliente.isPresent()) {
            // ResponseEntity.ok() retorna um 200 quando sucesso na API
            return ResponseEntity.ok(cliente.get()); // cliente.get() para obter o cliente que está dentro do Optional<>
        }
        // Retornando um 404
        return ResponseEntity.notFound().build();

        // FAZENDO EM UMA LINHA
        //return clientes.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Com o POST abaixo não preciso mais da CommandLineRunner p/ criar um cliente
    @PostMapping("salvar-cliente")
    @ResponseBody
    // Como estou recebendo um objeto Json p/ representar cliente, preciso colocar a anotação @RequestBody
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientesRepository.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("deletar-cliente-id/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> deleteClienteById(@PathVariable Integer id) {
        // Optional é usada pois pode existir ou não um cliente por este id
        Optional<Cliente> cliente = clientesRepository.findById(id);
        if(cliente.isPresent()) {
            clientesRepository.delete(cliente.get()); // Dando um get() no Optional<> para pegar o cliente retornado e excluí-lo
            // ResponseEntity.noContent() é um retorno 204 de sucesso mas que não retorna nenhum conteúdo
            return ResponseEntity.noContent().build();
        }
        // Retornando um 404
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/atualizar-cliente-id/{id}")
    @ResponseBody
    // Neste caso, não pude retornar um <Cliente>, por isso não deixei explícito
    public ResponseEntity updateClienteById( @PathVariable Integer id,
                                             @RequestBody Cliente cliente) {
        //clientes.findById(id) me retorna um Optional<T> e este permite que eu encadeie algumas chamadas, como o .map()
        return clientesRepository.findById(id).map(c -> {
            cliente.setId(c.getId()); // Ao invés de usar o set de cada atributo da classe, eu uso o setId e automaticamente atualizo todas as propriedades
            clientesRepository.save(cliente);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
        //o método .orElseGet() recebe um suplier. O suplier é um interface funcional que não recebe parametros e retorna qualquer coisa
    }

    @GetMapping("consulta-cliente-filtro")
    @ResponseBody
    public ResponseEntity<List<Cliente>> findByFiltro(Cliente filtro) { // Neste caso não pode ter o RequestBody. Se não retorna bad request
        // Definindo a estratégia de pesquisa (match)
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        // O Example permite que sejam extraídas as propriedades do objeto recebido. É um recurso do SPRING DATA que monta uma query dinâmica
        // com o que está presente no objeto e cria um example
        Example example = Example.of(filtro, matcher);
        return ResponseEntity.ok(clientesRepository.findAll(example)); // Se não encontrar nada, retorna uma lista vazia
    }
}
