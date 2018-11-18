package de.galytskyy.bot.service.worker;

import de.galytskyy.bot.entity.Config;
import de.galytskyy.bot.service.config.ConfigService;
import de.galytskyy.bot.service.notifier.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class PostmanServiceImpl implements PostmanService {

    private static final String SUBJECT = "Bot notification at: ";
    private final ConfigService configService;
    private final EmailService emailService;

    @Autowired
    public PostmanServiceImpl(ConfigService configService, EmailService emailService) {

        this.configService = configService;
        this.emailService = emailService;
    }

    public void sendRequest() {

        Config config = configService.getConfig();
        String result = "";
        ResponseEntity<String> responseEntity = doPost(config);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
            result = responseEntity.getBody();
        }
        String text = "Status Code: " + statusCode + " Result: " + result;
        emailService.sendSimpleMessage(config.getEmailTo(), SUBJECT + LocalDateTime.now(), text);
    }

    //If you have problem with https: http://magicmonster.com/kb/prg/java/ssl/pkix_path_building_failed.html
    private ResponseEntity<String> doPost(Config config) {

        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        config.getParams().get(0).forEach(params::add);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(config.getUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(params, headers);

        return restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                requestEntity,
                String.class);
    }
}
