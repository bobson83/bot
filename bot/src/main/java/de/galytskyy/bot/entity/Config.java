package de.galytskyy.bot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {

    private String url;
    private List<Map<String, String>> params;
    private String emailTo;

    // Json Deserializer only
    public Config() {

    }

    public Config(String url, List<Map<String, String>> params, String emailTo) {

        this.url = url;
        this.params = params;
        this.emailTo = emailTo;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public List<Map<String, String>> getParams() {

        return params;
    }

    public void setParams(List<Map<String, String>> params) {

        this.params = params;
    }

    public String getEmailTo() {

        return emailTo;
    }

    public void setEmailTo(String emailTo) {

        this.emailTo = emailTo;
    }
}
