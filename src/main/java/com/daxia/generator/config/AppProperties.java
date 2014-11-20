package com.daxia.generator.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class will read classpath file app.properties
 * 
 * @date 2013-2-14
 */
public class AppProperties {
    private static Logger logger = Logger.getLogger(AppProperties.class);
    private static Properties props = new Properties();
    static {
        try {
            InputStream is = AppProperties.class.getClassLoader().getResourceAsStream("app.properties");
            props.load(is);
        } catch (IOException e) {
            logger.error(e, e);
        }
    }
    
    public static String get(String key) {
        return props.getProperty(key);
    }
}
