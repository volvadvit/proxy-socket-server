package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    /** Load value from local.properties by its key
     *  Need to add "api.key", "db.url", "db.name", "collection.name"
     */
    private PropertyLoader() {
    }

    public static String get(String propertyName) {
        return loadProperty(propertyName);
    }

    private static String loadProperty(String name) {
        Properties property = new Properties();
        String result = "";

        try (FileInputStream fis = new FileInputStream("src/main/resources/local.properties")){
            property.load(fis);
            result = property.getProperty(name);

        } catch (IOException e) {
            System.err.println("Server ERROR   ::   File 'local.properties', with 'api.key' doesn't exist!");
        } finally {
            return result;
        }
    }
}
