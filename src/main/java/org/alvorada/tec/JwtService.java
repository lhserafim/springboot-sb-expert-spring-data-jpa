package org.alvorada.tec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.alvorada.tec.domain.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    // Injetando os valores do arquivo application.properties
    @Value("${security.jwt.expiracao}")
    private String expiracao; // Expiração do token. Aqui eu estou definindo em minutos, mas é livre para escolher milisegundos por ex.
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura; // É a chave que serve para decodificar o token

    // Método para gerar token para os usuários que forem devidamente autenticados
    public String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiracao); // Convertendo p/ long
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString); // Somando 30 minutos ao meu horário atual
        // Como a expiração é definida por um objeto do tipo Date, preciso converter meu dataHoraExpiracao em um instante
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        // E então converter em date
        Date data = Date.from(instant);
        // Montando o token
        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    // Apenas para testar uma aplicação rodando uma aplicação Standalone
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
        JwtService service = context.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);
    }
}
