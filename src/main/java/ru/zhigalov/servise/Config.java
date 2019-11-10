package ru.zhigalov.servise;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    public static void init()    {
        ClassLoader loader = Config.class.getClassLoader();
        InputStream io = loader.getResourceAsStream("properties.properties");
        try {
            properties.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }

}

