package org.alvorada.tec.domain.repository;

import org.alvorada.tec.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    // Constantes em caixa ALTA
    private static String INSERT = "INSERT INTO cliente (nome) values (?)"; // ? é um placeholder
    private static String SELECT_ALL = "SELECT * FROM cliente";

    @Autowired // p/ fazer a injeção
    private JdbcTemplate jdbcTemplate; // Usando pois não estou com o JPA configurado

    public Cliente salvar(Cliente cliente) {
        // o update permite que eu faça insert, update e delete
        // Estou passando 2 param. O segundo é um array de objetos para poder passar o nome. Dou o getNome no objeto recebido
        jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public List<Cliente> obterTodos() {
        // Recebe a query e um RowMapper. O RowMapper mapeia as colunas do resultado da query para a classe informada
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                // Pegar a coluna desejada do resultSet e mapeá-la na nova instancia, com base no construtor da classe
                return new Cliente(id, nome);
            }
        });
    }
}
