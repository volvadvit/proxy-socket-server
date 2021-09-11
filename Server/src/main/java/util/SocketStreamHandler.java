//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketStreamHandler implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public SocketStreamHandler(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.reader = this.createReader();
            this.writer = this.createWriter();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    private BufferedWriter createWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void writeLine(String message) {
        try {
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public String readLine() {
        try {
            return this.reader.readLine();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
    }

    public void close() throws IOException {
        this.writer.close();
        this.reader.close();
        this.socket.close();
    }
}
