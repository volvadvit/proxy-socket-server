import repository.CityWeatherRepo;
import service.CityWeatherService;
import util.PropertyLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String API_KEY = setUpServer();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("#################   Server started   #################\n");

            while (true) {
                try (Socket client = server.accept();
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(client.getInputStream()));
                        BufferedWriter writer =
                                     new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
                ){
                    System.out.println("#################   Get connection   #################\n");

                    // Handshake
                    String request = reader.readLine();
                    System.out.println("Request = " + request);
                    writer.write("Hello! Your request = " + request + ". Please, stand by...");
                    writer.newLine();
                    writer.flush();

                    StringBuilder response = new StringBuilder();

                    String result = new CityWeatherService(API_KEY).getInfo(request.toLowerCase());
                    response.append(result);

                    writer.write(response.toString());
                    writer.newLine();
                    writer.flush();
                } catch (NullPointerException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String setUpServer() {
        // time to live - 1 hour.
        CityWeatherRepo.countdownAndDropDB(3_600_000);
         return PropertyLoader.get("api.key");
    }
}
