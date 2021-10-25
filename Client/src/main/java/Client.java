import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws InterruptedException {

        try (Socket socket = new Socket("127.0.0.1", 5000);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())))
        {
            System.err.println("==========  Connected to server ========== \n");

            System.out.println("Input one city name, like \"Moscow, London, New York...\"");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String request = input.readLine();

            writer.write(request);
            writer.newLine();
            writer.flush();

            // Handshake
            String response = reader.readLine();
            System.out.println("\n" + response + "\n");

            StringBuilder forecast = new StringBuilder();

            int timeout = 0;
            while ((response = reader.readLine()) == null) {
                Thread.sleep(1000);

                timeout++;
                if (timeout == 10) {
                    System.err.println("TIMEOUT ERROR. SERVER IS NOT RESPONDING.\n Please, try later...");
                    System.exit(0);
                }
            }

            forecast.append(response).append("\n");
            while ((response = reader.readLine()) != null) {
                forecast.append(response).append("\n");
            }
            System.out.println(forecast);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
