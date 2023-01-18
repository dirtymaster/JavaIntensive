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
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        User creator = new User(1L, "user", "user",
                new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room
                = new Chatroom(1L, "room", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello !",
        Timestamp.from(Instant.now()));
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
        try {
            messagesRepository.save(message);
        } catch (Exception e) {
            System.err.println("Unable to add a message");
            System.exit(1);
        }
        System.out.println(message.getId());

    }
}
