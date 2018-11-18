package de.galytskyy.bot.service.notifier.email;

import de.galytskyy.bot.entity.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final EmailConfig emailConfig;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, EmailConfig emailConfig) {

        this.emailSender = emailSender;

        this.emailConfig = emailConfig;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public boolean isEmailConfigurationInvalid() {

        return "your_password".equals(emailConfig.getPassword()) ||
                "smtp.your_provider.com".equals(emailConfig.getHost()) ||
                "your_email".equals(emailConfig.getUsername());
    }
}
