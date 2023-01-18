package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        MessagesRepository messagesRepository = null;
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setLoginTimeout(3);
            Connection connection = dataSource.getConnection();
            messagesRepository = new MessagesRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database");
            System.exit(1);
        }
        Optional<Message>  messageOptional = null;
        try {
            messageOptional = messagesRepository.findById(1);
        } catch (SQLException e) {
            System.err.println("Message not found");
            System.exit(1);
        }
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Hi");
            message.setTime(null);
            try {
                messagesRepository.update(message);
            } catch (SQLException e) {
                System.err.println("Unable to update message");
                System.exit(1);
            }
        }
        Optional<Message> updatedMessage = null;
        try {
            updatedMessage = messagesRepository.findById(1);
        } catch (SQLException e) {
            System.err.println("Message not found");
            System.exit(1);
        }
        System.out.println(updatedMessage.get());
    }
}
