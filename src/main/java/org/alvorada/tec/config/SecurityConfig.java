package org.alvorada.tec.config;

import org.alvorada.tec.security.jwt.JwtAuthFilter;
import org.alvorada.tec.security.jwt.JwtService;
import org.alvorada.tec.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @Bean // Criptografar o password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Gera uma hash com a senha criptografada SEMPRE DIFERENTE
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

//    @Override // Para autenticar - MÉTODO BASIC
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // RECUPERANDO USUÁRIO DE UMA BASE DE DADOS -> NÃO FUNCIONOU!!!!!
//        auth.userDetailsService(userService) // carrega os usuários
//            .passwordEncoder(passwordEncoder()); // vai comprar a senha do usuário

        // RECUPERANDO USUÁRIO EM MEMÓRIA
//        auth.inMemoryAuthentication() // Configurando um usuário em memória
//                .passwordEncoder(passwordEncoder()) // Passando o enconder
//                .withUser("fulano") // Definindo o usuário
//                .password(passwordEncoder().encode("123")) // definindo e criptografando a senha
//                .roles("USER", "PRODUCAO"); // definindo o perfil do usuário
//    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/clientes-rest-controller/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos-rest-controller/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos-rest-controller/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/usuarios-rest-controller/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }
}
