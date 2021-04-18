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

    @Override // Para autorizar
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
