package org.alvorada.tec.config;

import org.alvorada.tec.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl userService;

    @Bean // Criptografar o password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Gera uma hash com a senha criptografada SEMPRE DIFERENTE
    }

    @Override // Para autenticar
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // RECUPERANDO USUÁRIO DE UMA BASE DE DADOS
        auth.userDetailsService(userService) // carrega os usuários
            .passwordEncoder(passwordEncoder()); // vai comprar a senha do usuário

        // RECUPERANDO USUÁRIO EM MEMÓRIA
//        auth.inMemoryAuthentication() // Configurando um usuário em memória
//                .passwordEncoder(passwordEncoder()) // Passando o enconder
//                .withUser("fulano") // Definindo o usuário
//                .password(passwordEncoder().encode("123")) // definindo e criptografando a senha
//                .roles("USER", "PRODUCAO"); // definindo o perfil do usuário
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); // Configuração padrão. Bloqueia tudo!
        http
                .csrf().disable() // Desabilito pq estou usando api stateless
                .authorizeRequests()
                .antMatchers("/api/clientes-rest-controller/**") // Todas as URLs que vierem a partir deste path
                    //.permitAll() // Dar acesso geral ao antMatchers informado
                    //.authenticated() // Se vc estiver autenticado ele da acesso (independente da role e authority)
                    .hasAnyRole("USER", "ADMIN")// Apenas que tiver a role
                    //.hasAuthority() // Apenas quem tiver a autorização (que fica dentro das roles)
                .antMatchers("/api/pedidos-rest-controller/**")
                    .hasRole("USER")
                .antMatchers("/api/produtos-rest-controller/**")
                    .hasAnyRole("USER", "PRODUCAO")
                .antMatchers(HttpMethod.POST,"/api/usuarios-rest-controller/**")
                    .permitAll()
            .and() // Toda vez que eu quiser voltar p/ a raiz (para o http) eu uso este and()
                //.formLogin(); // Formulário de login. se quiser, posso definir um formulário de login customizado
                .httpBasic(); // Outra forma de autenticação
    }
}
