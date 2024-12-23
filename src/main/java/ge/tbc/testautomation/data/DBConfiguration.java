package ge.tbc.testautomation.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfiguration {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = DBConfiguration.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("database.properties file not found.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Properties file could not be loaded.", e);
        }
    }

    public static String getURL() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
