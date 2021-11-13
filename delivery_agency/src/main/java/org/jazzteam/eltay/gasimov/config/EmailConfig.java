package org.jazzteam.eltay.gasimov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.jazzteam.eltay.gasimov")
public class EmailConfig {
    public static final String USERNAME = "gasdel.delivery.agency@gmail.com";
    private static final String PASSWORD = "lhvaxnquenclxfxg";

    private static final String MAIL_TRANSPORT_PROTOCOL_KEY = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String MAIL_SMTP_START_TLS_KEY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_SSL_TRUST_KEY = "mail.smtp.ssl.trust";
    private static final String MAIL_DEBUG_KEY = "mail.debug";
    private static final String SMTP_VALUE = "smtp";
    private static final String MAIL_SMTP_START_TLS_FLAG = "true";
    private static final String SMTP_GMAIL_COM_VALUE = "smtp.gmail.com";
    private static final int emailPort = 587;
    private static final String emailHost = "smtp.gmail.com";
    private static final boolean MAIL_DEBUG_FLAG = false;


    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);

        Properties properties = new Properties();
        properties.put(MAIL_TRANSPORT_PROTOCOL_KEY, SMTP_VALUE);
        properties.put(MAIL_SMTP_AUTH_KEY, MAIL_SMTP_START_TLS_FLAG);
        properties.put(MAIL_SMTP_START_TLS_KEY, MAIL_SMTP_START_TLS_FLAG);
        properties.put(MAIL_SMTP_SSL_TRUST_KEY, SMTP_GMAIL_COM_VALUE);
        properties.put(MAIL_DEBUG_KEY, MAIL_DEBUG_FLAG);
        properties.put("mail.smtp.port", emailPort);
        properties.put("mail.smtp.host", emailHost);

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("/templates/");
        return bean;
    }

}
