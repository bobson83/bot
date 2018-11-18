package de.galytskyy.bot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.galytskyy.bot.entity.Config;
import de.galytskyy.bot.entity.NotificationDto;
import de.galytskyy.bot.service.config.ConfigService;
import de.galytskyy.bot.service.notifier.email.EmailService;
import de.galytskyy.bot.service.worker.PostmanService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

@SpringBootApplication
public class BotApplication {

    private static final String SUBJECT = "Bot notification at: ";

    public static void main(String[] args) {

        SpringApplication.run(BotApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmailService emailService, ConfigService configService, PostmanService postmanService, ConfigurableApplicationContext ctx) {

        return args -> {

            DeserializeConfig(configService);

            NotificationDto notificationDto;

            if (configService.IsConfigInvalid()) {

                System.out.println("Config is invalid, please set valid config!");
                terminate(ctx, "Terminated with errors!");
                return;
            } else {

                System.out.println("Send Request...");
                notificationDto = postmanService.sendRequest();
            }

            if (emailService.isEmailConfigurationInvalid() || notificationDto == null) {

                System.out.println("ERROR: Email configuration is not set or is not valid!");
                System.out.println("Please set valid email configuration in application.properties");
                terminate(ctx, "Terminated with errors!");
                return;
            } else {

                emailService.sendSimpleMessage(notificationDto.getEmailTo(), SUBJECT + LocalDateTime.now(), notificationDto.toString());
                System.out.println("Send Notification...");
            }

            terminate(ctx, "Terminated successfully!");
        };
    }

    private void DeserializeConfig(ConfigService configService) {

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
    }

    private void terminate(ConfigurableApplicationContext ctx, String message) {

        ctx.close();
        System.out.println(message);
    }
}
