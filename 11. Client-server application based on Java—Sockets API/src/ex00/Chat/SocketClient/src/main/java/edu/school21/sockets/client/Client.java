package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter printWriter;
    private final Scanner serverScanner;
    private final Scanner scanner = new Scanner(System.in);

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        serverScanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() throws IOException {
        receiveMessage();
        for (int i = 0; i < 3; ++i) {
            sendMessage();
            receiveMessage();
        }

        serverScanner.close();
        printWriter.close();
        scanner.close();
        socket.close();
    }

    public void receiveMessage() throws IOException {
        if (serverScanner.hasNextLine()) {
            String message = serverScanner.nextLine();
            System.out.println(message);
        }
    }

    public void sendMessage() {
        if (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            printWriter.println(message);
        }
    }
}
