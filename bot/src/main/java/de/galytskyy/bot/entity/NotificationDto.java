package de.galytskyy.bot.entity;

import org.springframework.http.HttpStatus;

public class NotificationDto {

    private final HttpStatus statusCode;
    private final String emailTo;
    private final String result;

    public NotificationDto(String result, HttpStatus statusCode, String emailTo) {

        this.statusCode = statusCode;
        this.emailTo = emailTo;
        this.result = result;
    }

    public String getEmailTo() {

        return emailTo;
    }

    @Override
    public String toString() {

        return "Status Code: " + statusCode + " Result: " + result;
    }
}
