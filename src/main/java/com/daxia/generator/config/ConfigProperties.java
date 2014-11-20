package com.daxia.generator.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class will read config files defined in app.properties(config.properties.file)
 * 
 * @date 2013-2-14
 */
public class ConfigProperties {
    private static Logger logger = Logger.getLogger(ConfigProperties.class);
    private static Properties props = new Properties();
    static {
        String configPropertiesFile = AppProperties.get(KConstants.KEY_CONFIG_PROPERTIES_FILE);
        if (StringUtils.isNotBlank(configPropertiesFile)) {
            String[] files = configPropertiesFile.split(",");
            for (String file : files) {
                file = file.trim();
                Properties littleProps = new Properties();
                try {
                    littleProps.load(ConfigProperties.class.getClassLoader().getResourceAsStream(file));
                    Set<Entry<Object, Object>> entrySet = littleProps.entrySet();
                    for (Entry<Object, Object> entry : entrySet) {
                        props.put(entry.getKey().toString(), entry.getValue().toString());
                    }
                } catch (IOException e) {
                    logger.error(e, e);
                }
            }
        }
    }
    
    public static String get(String key) {
        return props.getProperty(key);
    }
}
