package de.galytskyy.bot.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.host}")
    private String host;

    public String getPassword() {

        return password;
    }

    public String getUsername() {

        return username;
    }

    public String getHost() {

        return host;
    }
}
