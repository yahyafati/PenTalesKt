package org.example;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;

public class Configuration implements Serializable {

    private static Configuration INSTANCE = null;

    private final String configurationFilePath;
    private Map<String, Object> configMap;

    private Configuration() {
        URL configURL = getClass().getResource("/application.yml");
        if (configURL == null) {
            throw new RuntimeException("Could not find configuration file");
        }
        configurationFilePath = configURL.getPath();
        initializeConfiguration();
    }

    public static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }

    private void initializeConfiguration() {
        try (FileInputStream fis = new FileInputStream(configurationFilePath)) {
            Yaml yaml = new Yaml();
            this.configMap = yaml.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String... keys) {
        var _configMap = this.configMap;
        Object result = null;
        for (String key : keys) {
            result = _configMap.get(key);
            if (result instanceof Map) {
                _configMap = (Map<String, Object>) result;
            }
        }
        return result;
    }

    public String getConnectionString() {
        return (String) get("url");
    }


}
