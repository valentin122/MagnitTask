package ru.zhigalov.service;

import java.io.*;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    public  void init()    {
        ClassLoader loader = Config.class.getClassLoader();
        InputStream io = loader.getResourceAsStream("properties.properties");
        try {
            properties.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

}

