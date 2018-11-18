package de.galytskyy.bot.service.config;

import de.galytskyy.bot.entity.Config;

public interface ConfigService {

    Config getConfig();

    void saveConfig(Config configs);
}
