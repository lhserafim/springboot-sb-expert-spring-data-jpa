package org.alvorada.tec.rest.controller;

import org.alvorada.tec.domain.entity.Produto;
import org.alvorada.tec.domain.repository.ProdutosRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos-rest-controller")
public class ProdutoRestController {

    private final ProdutosRepository produtosRepository;

    public ProdutoRestController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @GetMapping("consulta-produto-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtosRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @GetMapping("{id}")
    public ResponseEntity<Produto> getProdutoId(@PathVariable Integer id) {
        /*return produtos
                .findById(id)
                .map(produto -> {
                    return ResponseEntity.ok(produto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
         */
        // Fazendo com method reference e usando orElseThrow para trazer o status correto do erro + mensagem!
        return produtosRepository
                .findById(id)
                .map(ResponseEntity::ok) // O .map() vai retornar uma instancia de produto
                //.orElseGet(() -> ResponseEntity.notFound().build());
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping("salvar-produto")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto) {
        return produtosRepository.save(produto);
    }

    @PostMapping("salvar")
    public ResponseEntity<Produto> salvarProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtosRepository.save(produto));
    }

    @DeleteMapping("deletar-produto-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProdutoById(@PathVariable Integer id) {
        produtosRepository
                .findById(id)
                .map(p -> {
                    produtosRepository.deleteById(id);
                    return p;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não localizado para exclusão"));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Produto> deletar(@PathVariable Integer id) {
       // Desse jeito eu estou retornando o objeto deletado
       return ResponseEntity.ok(produtosRepository
                                    .findById(id)
                                    .map(p -> {
                                        produtosRepository.deleteById(id);
                                        return p;
                                    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não localizado para exclusão")));
    }

    @PutMapping("atualizar-produto/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto updateProdutoById(@PathVariable Integer id,
                                     @RequestBody Produto produto) {
        return produtosRepository.findById(id).map(p -> {
            produto.setId(p.getId());
            produtosRepository.save(produto);
            return p;
            // eu volto o p, porque o p corresponde ao dado que está no banco e foi atualizado. Se eu trouxesse o produto,
            // eu estaria trazendo o valor de entrada, ou seja, não teria garantias da efetividade da tansação
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Integer id,
                                                    @RequestBody Produto produto) {
        return ResponseEntity.ok(produtosRepository.findById(id).map(p -> {
            produto.setId(p.getId());
            produtosRepository.save(produto);
            return p;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado")));
    }

    @GetMapping("consulta-produto-filtro")
    public List<Produto> getProdutoFiltro(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }

}
