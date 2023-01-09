package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

@Component
public class Server {
    private final UsersService usersService;
    private PrintWriter printWriter;
    private Scanner scanner;
    private ServerSocket serverSocket;
    private Socket socket;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            scanner = new Scanner(socket.getInputStream());
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("Hello from Server!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        while (true) {
            try {
                assert scanner != null;
                if (scanner.hasNextLine()) {
                    String signUp = scanner.nextLine().trim();

                    if (!signUp.equals("signUp")) {
                        printWriter.println(
                                "You should have entered \"signUp\"");
                        System.exit(1);
                    }
                }
                String username = null;
                printWriter.println("Enter username:");
                if (scanner.hasNextLine()) {
                    username = scanner.nextLine().trim();
                }

                printWriter.println("Enter password:");
                String password = null;
                if (scanner.hasNextLine()) {
                    password = scanner.nextLine().trim();
                }
                usersService.signUp(username, password);
                printWriter.println("Successful!");

                if (scanner != null) {
                    scanner.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (Exception e) {
                printWriter.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}
