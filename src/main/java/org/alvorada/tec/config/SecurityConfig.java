package org.alvorada.tec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // Criptografar o password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Gera uma hash com a senha criptografada SEMPRE DIFERENTE
    }

    @Override // Para autenticar
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // Configurando um usuário em memória
                .passwordEncoder(passwordEncoder()) // Passando o enconder
                .withUser("fulano") // Definindo o usuário
                .password(passwordEncoder().encode("123")) // definindo e criptografando a senha
                .roles("USER"); // definindo o perfil do usuário
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http); // Configuração padrão. Bloqueia tudo!
        http
                .csrf().disable() // Desabilito pq estou usando api stateless
                .authorizeRequests()
                .antMatchers("/api/clientes-rest-controller/**") // Todas as URLs que vierem a partir deste path
                    //.permitAll(); // Dar acesso geral ao antMatchers informado
                    //.authenticated() // Se vc estiver autenticado ele da acesso (independente da role e authority)
                    .hasAnyRole("USER", "ADMIN")// Apenas que tiver a role
                    //.hasAuthority() // Apenas quem tiver a autorização (que fica dentro das roles)
                .antMatchers("/api/pedidos-rest-controller/**")
                    .hasRole("USER")
                .antMatchers("/api/produtos-rest-controller/**")
                    .hasRole("PRODUCAO")
            .and() // Toda vez que eu quiser voltar p/ a raiz (para o http) eu uso este and()
                .formLogin(); // se quiser, posso definir um formulário de login customizado
    }
}
