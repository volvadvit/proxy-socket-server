package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    /** Load value from local.properties by its key
     *  Need to add "api.key", "db.url", "db.name", "collection.name"
     */

    private final static String path = Thread.currentThread()
            .getContextClassLoader().getResource("").getPath() + "local.properties";

    public static String get(String propertyName) {
        return loadProperty(propertyName);
    }

    private static String loadProperty(String name) {
        Properties property = new Properties();
        String result = "";

        try (InputStream fis = new FileInputStream(path)){
            property.load(fis);
            result = property.getProperty(name);
        } catch (IOException e) {
            System.err.println("PropertiesLoader ERROR " + e.getMessage());
        } finally {
            return result;
        }
    }
}
