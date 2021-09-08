import util.SocketStreamHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

public class Server {

    private static String API_KEY = "";

    public static void main(String[] args) {
        API_KEY = loadApiKey();
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started");

            while (true) {
                    try (SocketStreamHandler socketStreamHandler = new SocketStreamHandler(serverSocket)) {
                        new Thread(() -> {
                            String request = socketStreamHandler.readLine();
                            System.out.println("Request   ::   " + request);

                            StringBuilder response = new StringBuilder("Hello! Your request = " + request + ". Please, stand by...");
                            response.append(getInfo(request));
                            System.out.println("Response   ::   " + response);

                            socketStreamHandler.writeLine(response.toString());
                        }).start();
                    } catch (IOException | NullPointerException e) {
                        System.err.println("Server Request/Response ERROR   ::   " + e.getMessage());
                    }
            }
        } catch (IOException e) {
            System.err.println("Server Socket ERROR   ::   " + e.getMessage());
        }
    }

    private static String loadApiKey() {
        Properties property = new Properties();
        String key = "API key";

        try (FileInputStream fis = new FileInputStream("src/main/resources/local.properties")){
            property.load(fis);

            key = property.getProperty("api.key");

        } catch (IOException e) {
            System.err.println("Server ERROR   ::   File 'local.properties', with 'api.key' doesn't exist!");
        } finally {
            return key;
        }
    }

    private static String getInfo(String location) {
        String result = checkServerCache();
        // TODO проверить на наличие данных в кэше
        if (result.equals("")) {
            try {
                URL url = new URL("api.openweathermap.org/data/2.5/weather?q="
                        + location + "&appid=" + API_KEY);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())))
                {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine);
                    }
                    result = parseJson(response.toString());

                    uploadDataStorage(result);
                }
            } catch (IOException e) {
                System.err.println("HTTP/URL CONNECTION ERROR   ::   " + e.getMessage());;
            }
        }
        return result;
    }

    private static void uploadDataStorage(String result) {
        //TODO save json in MongoDB file
    }

    private static String parseJson(String response) {
        return "";
    }

    private static String checkServerCache() {
        return "";
    }
}
