package de.galytskyy.bot.service.config;

import de.galytskyy.bot.entity.Config;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

    private Config config;

    public Config getConfig() {

        return config != null ? config : getDefaultConfig();
    }

    public void saveConfig(Config config) {

        this.config = config;
    }

    private Config getDefaultConfig() {

        List params = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        params.add(map);

        return new Config("dummyUrl", params, "dummy@email");
    }
}
