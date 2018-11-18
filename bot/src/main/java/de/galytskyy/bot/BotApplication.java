package de.galytskyy.bot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.galytskyy.bot.entity.Config;
import de.galytskyy.bot.service.config.ConfigService;
import de.galytskyy.bot.service.worker.PostmanService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class BotApplication {

    public static void main(String[] args) {

        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ConfigService configService, PostmanService postmanService, ConfigurableApplicationContext ctx) {

        return args -> {
            // read json and write to config object
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Config> typeReference = new TypeReference<Config>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/config.json");
            try {
                Config config = mapper.readValue(inputStream, typeReference);
                configService.saveConfig(config);
                System.out.println("Configs Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save config: " + e.getMessage());
            }

            System.out.println("Working...");
            postmanService.sendRequest();

            System.out.println("Terminated!");
            ctx.close();
        };
    }
}
