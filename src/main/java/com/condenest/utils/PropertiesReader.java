package com.condenest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * This class is used to read the config properties file
 */

public class PropertiesReader {
    public static Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("./src/main/resources/config.properties"));
            if (Objects.isNull(properties)) {
                properties = new Properties();
                properties.load(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
