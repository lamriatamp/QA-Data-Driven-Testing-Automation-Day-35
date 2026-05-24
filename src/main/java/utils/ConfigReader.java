package utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigReader {


    private static Properties properties;


    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Gagal memuat file config.properties di: src/test/resources/config.properties", e);
        }
    }


    public static String getProperty(String key) {
        return properties.getProperty(key);
    }


    public static void printAllProperties() {
        properties.forEach((k, v) -> System.out.println(k + " = " + v));
    }




}
