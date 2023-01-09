package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Server {
    private final UsersService usersService;
    private ServerSocket serverSocket;
    private final List<Client> clients = new ArrayList<>();

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

            while(true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clients.add(client);
                client.start();
            }
        } catch (Exception ignored) {
        }
    }

    private synchronized void sendMessageToAll(String sender, String message) {
        usersService.createMessage(sender, message);
        clients.stream().filter(c -> c.active)
                .forEach(c -> c.printWriter.println(sender + ": " + message));
    }

    private void removeClient(Client client) throws IOException {
        clients.remove(client);
        if (clients.isEmpty()) {
            serverSocket.close();
            System.exit(0);
        }
    }

    private class Client extends Thread {
        private PrintWriter printWriter;
        private Scanner scanner;
        private Socket socket;
        private String username;
        private String password;
        private boolean active;

        Client(Socket socket) {
            try {
                this.socket = socket;
                scanner = new Scanner(socket.getInputStream());
                printWriter = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ignored) {
            }
        }

        @Override
        public void run() {
            printWriter.println("Hello from Server!");

            while (true) {
                try {
                    if (scanner.hasNextLine()) {
                        String message = scanner.nextLine().trim();

                        if (message.equals("signUp")) {
                            getUsersCredentials();
                            usersService.signUp(new User(username, password));
                            printWriter.println("Successful!");
                        } else if (message.equals("signIn")) {
                            getUsersCredentials();

                            if (usersService.signIn(username, password)) {
                                printWriter.println("Start messaging");
                                processMessage();
                            }
                            break;
                        } else if (message.equals("Exit")) {
                            exitChat();
                            break;
                        } else {
                            printWriter.println("Invalid command");
                            System.exit(1);
                        }
                    }
                } catch (RuntimeException ignored) {
                }
            }
        }

        private void getUsersCredentials() {
            printWriter.println("Enter username: ");
            username = scanner.nextLine().trim();

            printWriter.println("Enter password: ");
            password = scanner.nextLine().trim();
        }

        private void processMessage() {
            while (true) {
                active = true;
                String message = scanner.nextLine().trim();

                if (message.equals("Exit")) {
                    exitChat();
                    return;
                }
                sendMessageToAll(username,message);
            }
        }

        private void exitChat() {
            try {
                printWriter.println("You have left the chat.");
                removeClient(this);
                scanner.close();
                printWriter.close();
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
