package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class Clientes {

    @Autowired // p/ fazer a injeção (IOC)
    private EntityManager entityManager;

    // Necessário adicionar a dependência no Maven para esta annotation import org.springframework.transaction.annotation.Transactional;
    @Transactional // o EntityManager precisa que eu informe que o método é transacional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        //Para evitar o erro: Removing a detached instance, que acontece quando a entidade está transiente, devemos fazer o merge
        //Se o entityManager não contiver estea instancia de cliente, ou seja se não estiver sincronizado, fazer o merge
        if(!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();
    }

    // usando o readOnly a pesquisa fica mais rápida pq o JPA não faz cache
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE :nome";
        // Como eu tenho parâmetro, eu preciso criar uma query tipada, passando a query jpql e a classe que ele vai retornar
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Integer id) {
        return entityManager.find(Cliente.class, id);
    }

}
