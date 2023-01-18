package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        long id = -1;
        System.out.println("Enter a message ID");
        try {
            Scanner scanner = new Scanner(System.in);
            id = scanner.nextLong();
            if (id <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        MessagesRepository messagesRepository = null;
        Connection connection = null;
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setLoginTimeout(3);
            connection = dataSource.getConnection();
            messagesRepository = new MessagesRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database");
            System.exit(1);
        }
        try {
            Optional<Message> optionalMessage = messagesRepository.findById(id);
            if (optionalMessage.isPresent()) {
                System.out.println(optionalMessage.get());
            } else {
                System.out.println("Message not found");
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Unable to find message");
            System.exit(1);
        }
    }
}
