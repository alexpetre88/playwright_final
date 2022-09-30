package services;

import java.io.IOException;
import java.util.Properties;

public class EnvironmentReaderService {

    private static Properties props = new Properties();

    static {

        String envFile = System.getProperty("env");
        if(envFile == null) {
            envFile = "production";
        }
        String filePath = envFile.concat(".properties");

        try {
            props.load(EnvironmentReaderService.class.getClassLoader().getResourceAsStream(filePath));
        } catch (IOException e) {
            System.out.println("Did not manage to read properties file");
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
