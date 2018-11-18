package de.galytskyy.bot.service.notifier.email;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    boolean isEmailConfigurationInvalid();
}
