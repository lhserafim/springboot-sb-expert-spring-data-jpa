package org.alvorada.tec.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * Este arquivo de configuração foi criado para que as mensagens criadas em messages.properties sejam interpoladas
 * nas validações Bean das entidades
 * */

@Configuration
public class InternacionalizacaoConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // Arquivo contendo as mensagens padrões
        messageSource.setDefaultEncoding("UTF-8"); // Codificação BR que aceita assentos. ISO-8859-1 não funcionou
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    @Bean // o método abaixo é que faz a interpolação entre arquivo e entidades
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}