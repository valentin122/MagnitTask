package ru.zhigalov;

import java.io.*;
import java.util.Properties;

public class Settings {

    private static final Properties prop = new Properties();

    public static void load()    {
        ClassLoader loader = Settings.class.getClassLoader();
        InputStream io = loader.getResourceAsStream("properties.properties");
        try {
            prop.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }

}

