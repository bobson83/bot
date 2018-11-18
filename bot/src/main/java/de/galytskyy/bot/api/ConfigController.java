package de.galytskyy.bot.api;

import de.galytskyy.bot.entity.Config;
import de.galytskyy.bot.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
class ConfigController {

    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {

        this.configService = configService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Config GetConfig() {

        return configService.getConfig();
    }
}
