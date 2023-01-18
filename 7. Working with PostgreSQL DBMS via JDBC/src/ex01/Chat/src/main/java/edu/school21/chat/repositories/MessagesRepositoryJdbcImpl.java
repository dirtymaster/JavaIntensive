package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final Connection connection;

    public MessagesRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public Optional<Message> findById(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from message where id = " + id);
        ResultSet messageSet = preparedStatement.executeQuery();
        if (messageSet.next()) {
            long messageId = messageSet.getLong(1);
            User author = getAuthor(messageSet.getLong(2));
            Chatroom chatroom = getRoom(messageSet.getLong(3));
            String text = messageSet.getString(4);
            Timestamp time = messageSet.getTimestamp(5);
            return Optional.of(
                    new Message(messageId, author, chatroom, text, time));
        } else {
            return Optional.empty();
        }
    }

    private User getAuthor(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"User\" where id = " + id);
        ResultSet userSet = preparedStatement.executeQuery();
        if (userSet.next()) {
            return new User(userSet.getLong(1), userSet.getString(2),
                    userSet.getString(3), null, null);
        } else {
            return null;
        }
    }

    private Chatroom getRoom(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from chatroom where id = " + id);
        ResultSet roomSet = preparedStatement.executeQuery();
        if (roomSet.next()) {
            return new Chatroom(roomSet.getLong(1), roomSet.getString(2),
                    null, null);
        } else {
            return null;
        }
    }
}
