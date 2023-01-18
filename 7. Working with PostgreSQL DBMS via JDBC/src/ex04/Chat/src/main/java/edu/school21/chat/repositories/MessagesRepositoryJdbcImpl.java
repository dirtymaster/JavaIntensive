package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final Connection connection;

    public MessagesRepositoryJdbcImpl(Connection connection)
            throws SQLException {
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

    @Override
    public void save(Message message) throws SQLException {
        checkUser(message);
        checkRoom(message);

        message.setId(getNewMessageId());
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into message values (" +
                        message.getId() + "," +
                        message.getAuthor().getId() + "," +
                        message.getRoom().getId() + ",'" +
                        message.getText() + "','" +
                        message.getTime() + "'::timestamp)");
        preparedStatement.execute();
    }

    public void checkUser(Message message) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"User\" where id ="
                        + message.getAuthor().getId());
        ResultSet userSet = preparedStatement.executeQuery();
        if (!userSet.next()) {
            throw new NotSavedSubEntityException();
        }
    }

    public void checkRoom(Message message) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from chatroom where id ="
                        + message.getRoom().getId());
        ResultSet roomSet = preparedStatement.executeQuery();
        if (!roomSet.next()) {
            throw new NotSavedSubEntityException();
        }
    }

    public long getNewMessageId() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select max(id) + 1 from message");
        ResultSet idSet = preparedStatement.executeQuery();
        idSet.next();
        return idSet.getLong(1);
    }

    @Override
    public void update(Message message) throws SQLException {
        if (getMessageId(message) != null) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update message set author_id = "
                            + message.getAuthor().getId() + ", room_id = "
                            + message.getRoom().getId() + ", text = "
                            + (message.getText() == null ? null
                            : "'" + message.getText() + "'") + ", time = "
                            + (message.getTime() == null ? null
                            : "'" + message.getTime() + "'::timestamp")
                            + " where id = " + message.getId());
            preparedStatement.execute();
        }
    }

    public Long getMessageId(Message message) throws SQLException {
        long messageId = message.getId();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from message where id = " + messageId);
        ResultSet messageSet = preparedStatement.executeQuery();
        return messageSet.next() ? messageId : null;
    }
}
