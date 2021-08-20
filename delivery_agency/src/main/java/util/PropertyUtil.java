package util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertyUtil {
    private static final String CONFIG_PROPERTIES_FILE_LOCATION = "config.properties";
    private static final Properties properties = new Properties();


    static {
        loadProperties();
    }

    public PropertyUtil() {
    }

    private static void loadProperties(){

        try {
            try (InputStream propertiesStream = PropertyUtil.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_PROPERTIES_FILE_LOCATION)) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException("Config property file not found!!!");
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
