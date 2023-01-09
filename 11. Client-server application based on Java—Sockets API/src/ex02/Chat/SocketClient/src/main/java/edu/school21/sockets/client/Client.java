package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter printWriter;
    private final Scanner scanner;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        scanner = new Scanner(socket.getInputStream());
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        ServerWriter serverWriter = new ServerWriter(printWriter);
        ServerReader serverReader = new ServerReader(scanner, serverWriter);
        serverReader.start();
        serverWriter.start();

        try {
            serverReader.join();
            serverWriter.join();
            socket.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static class ServerReader extends Thread {
        private final Scanner serverScanner;
        ServerWriter serverWriter;

        public ServerReader(Scanner serverScanner, ServerWriter serverWriter) {
            this.serverScanner = serverScanner;
            this.serverWriter = serverWriter;
        }

        @Override
        public void run() {
            try {
                receiveMessage();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        private void receiveMessage() throws IOException {
            while (serverScanner.hasNextLine()) {
                String message = serverScanner.nextLine();
                System.out.println(message);

                if (message.equals("Exit")) {
                    serverScanner.close();
                    serverWriter.active = false;
                    break;
                }
            }
        }
    }

    private static class ServerWriter extends Thread {
        private final PrintWriter serverPrintWriter;
        private final Scanner scanner = new Scanner(System.in);
        private boolean active = true;

        public ServerWriter(PrintWriter printWriter) {
            this.serverPrintWriter = printWriter;
        }

        @Override
        public void run() {
            try {
                sendMessage();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        private void sendMessage() {
            while (active) {
                String message = scanner.nextLine();
                serverPrintWriter.println(message);

                if (message.equals("Exit")) {
                    System.exit(0);
                }
            }
            serverPrintWriter.close();
        }
    }
}
