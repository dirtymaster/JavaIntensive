package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.Service;
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
    private final Service service;
    private ServerSocket serverSocket;
    private final List<Client> clients = new ArrayList<>();

    @Autowired
    public Server(Service service) {
        this.service = service;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                clients.add(client);
                client.start();
            }
        } catch (Exception ignored) {
        }
    }

    private synchronized void sendMessageToAll(String sender, String message,
                                               Long roomId) {
        service.createMessage(sender, message, roomId);
        clients.stream().filter(c -> c.active)
                .filter(c -> c.currentRoom.getId().equals(roomId))
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
        private Room currentRoom;

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

            label:
            while (true) {
                printWriter.println("    1. SignIn");
                printWriter.println("    2. SignUp");
                printWriter.println("    3. Exit");
                try {
                    if (scanner.hasNextLine()) {
                        String message = scanner.nextLine().trim();

                        switch (message) {
                            case "2":
                                getUsersCredentials();
                                service.signUp(new User(null, username, password));
                                printWriter.println("Successful!");
                                break;
                            case "1":
                                getUsersCredentials();

                                if (service.signIn(username, password)) {
                                    processRoom();
                                    processMessage();
                                } else {
                                    printWriter.println("Invalid credentials");
                                }
                                break label;
                            case "3":
                                exitChat();
                                break label;
                            default:
                                printWriter.println("Invalid command");
                                break;
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

        private void processRoom() {
            label:
            while (true) {
                printWriter.println("    1. Create room");
                printWriter.println("    2. Choose room");
                printWriter.println("    3. Exit");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        createRoom();
                        break label;
                    case "2":
                        if (chooseRoom()) {
                            break label;
                        } else {
                            printWriter.println("Rooms list is empty");
                        }
                        break;
                    case "3":
                        exitChat();
                        break label;
                    default:
                        printWriter.println("Invalid command");
                        break;
                }
            }
        }

        private void createRoom() {
            printWriter.println("Enter name:");
            String roomName = scanner.nextLine().trim();
            service.createRoom(roomName);
            currentRoom = service.getRoomByName(roomName);
            printWriter.println(roomName + " ---");
        }

        private boolean chooseRoom() {
            List<Room> rooms = service.getAllRooms();
            if (rooms.isEmpty()) {
                return false;
            }
            printWriter.println("Rooms:");
            for (int i = 1; i <= rooms.size(); ++i) {
                printWriter.println(
                        "    " + i + ". " + rooms.get(i - 1).getName());
            }
            printWriter.println("    " + (rooms.size() + 1) + ". Exit");
            Long roomId = null;
            try {
                String roomIdString = scanner.nextLine().trim();
                roomId = (long) Integer.parseInt(roomIdString);
            } catch (NumberFormatException e) {
                printWriter.println("Invalid command");
                System.exit(1);
            }
            if (roomId < 1 || roomId > rooms.size()) {
                printWriter.println("Invalid number of room");
                System.exit(1);
            }
            currentRoom = service.getRoomById(roomId);
            printWriter.println(currentRoom.getName() + " ---");
            showLastMessages(30);
            return true;
        }

        private void showLastMessages(int numberOfMessages) {
            List<Message> messages = service.getMessagesByRoomId(
                    currentRoom.getId(), numberOfMessages);
            for (Message message : messages) {
                printWriter.println(
                        message.getSender() + ": " + message.getText());
            }
        }

        private void processMessage() {
            printWriter.println("Start messaging");
            while (true) {
                active = true;
                String message = scanner.nextLine().trim();

                if (message.equals("Exit")) {
                    exitChat();
                    return;
                }
                sendMessageToAll(username, message, currentRoom.getId());
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
