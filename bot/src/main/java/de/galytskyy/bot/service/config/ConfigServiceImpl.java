package de.galytskyy.bot.service.config;

import de.galytskyy.bot.entity.Config;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    private Config config;

    public Config getConfig() {

        return config;
    }

    public void saveConfig(Config config) {

        this.config = config;
    }

    @Override
    public boolean IsConfigInvalid() {

        return config == null || "https://your_path".equals(config.getUrl());
    }
}
