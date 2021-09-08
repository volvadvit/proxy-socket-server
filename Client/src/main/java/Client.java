import util.SocketStreamHandler;

import java.io.*;

public class Client {
    public static void main(String[] args) {
        try (SocketStreamHandler socketHandler = new SocketStreamHandler("127.0.0.1", 5000)) {
            System.out.println("Connected to server");

            String request = new BufferedReader(new InputStreamReader(System.in)).readLine();
            socketHandler.writeLine(request);

            String serverResponse = socketHandler.readLine();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.err.println("Client's connection to server ERROR   ::   " + e.getMessage());
        }
    }
}
