import dao.CityWeatherRepo;
import service.CityWeatherService;
import util.PropertyLoader;
import util.SocketStreamHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {

            final String API_KEY = setUpServer();;
            System.out.println("#################   Server started   #######################\n");

            while (true) {
                    try (SocketStreamHandler socketStreamHandler = new SocketStreamHandler(serverSocket)) {
                        new Thread(() -> {
                            String request = socketStreamHandler.readLine();
                            System.out.println("Request   ::   " + request);

                            socketStreamHandler.writeLine("Hello! Your request = " + request + ". Please, stand by...");

                            String response = new CityWeatherService(API_KEY).getInfo(request);
                            System.out.println("Response   ::   " + response);
                            socketStreamHandler.writeLine(response);
                        }).start();
                    } catch (IOException | NullPointerException e) {
                        System.err.println("Server Request/Response ERROR   ::   " + e.getMessage());
                    }
            }
        } catch (IOException e) {
            System.err.println("Server Socket ERROR   ::   " + e.getMessage());
        }
    }

    private static String setUpServer() {
        CityWeatherRepo.countdownAndDrop(60000);
         return PropertyLoader.get("api.key");
    }
}
