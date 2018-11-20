package de.galytskyy.bot.service.worker;

import de.galytskyy.bot.entity.Config;
import de.galytskyy.bot.entity.NotificationDto;
import de.galytskyy.bot.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PostmanServiceImpl implements PostmanService {

    private final ConfigService configService;

    @Autowired
    public PostmanServiceImpl(ConfigService configService) {

        this.configService = configService;
    }

    public NotificationDto sendRequest() {

        Config config = configService.getConfig();
        String result = "";
        ResponseEntity<String> responseEntity = doPost(config);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            result = responseEntity.getBody();
        }

        return new NotificationDto(result, statusCode, config.getEmailTo());
    }

    //If you have problem with https: http://magicmonster.com/kb/prg/java/ssl/pkix_path_building_failed.html
    private ResponseEntity<String> doPost(Config config) {

        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        config.getParams().forEach(innerMap -> innerMap.forEach(params::add));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(config.getUrl());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(params, headers);

        return restTemplate.postForEntity(
                builder.build().encode().toUri(),
                requestEntity,
                String.class);
    }
}
