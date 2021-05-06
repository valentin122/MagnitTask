package ru.zhigalov.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();
    private static final Logger LOG = LogManager.getLogger(Config.class.getName());

    public  void init()    {
        ClassLoader loader = Config.class.getClassLoader();
        InputStream io = loader.getResourceAsStream("properties.properties");
        try {
            properties.load(io);
        } catch (Exception e) {
            LOG.error("Can't read properties.properties", e);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}

