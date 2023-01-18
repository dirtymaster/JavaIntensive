package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final Connection connection;

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll(int page, int size) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"User\" where id between "
                        + ((page - 1) * size + 1) + " and " + (page * size)
                        + " order by id");
        ResultSet userSet = preparedStatement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (userSet.next()) {
            long userId = userSet.getLong(1);
            userList.add(new User(userId, userSet.getString(2),
                    userSet.getString(3), findUserCreatedRooms(userId),
                    findUsersSocializationRooms(userId)));
        }
        return userList;
    }

    private List<Chatroom> findUserCreatedRooms(long userId)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from chatroom where owner = " + userId);
        ResultSet roomsSet = preparedStatement.executeQuery();
        List<Chatroom> chatroomList = new ArrayList<>();
        while (roomsSet.next()) {
            chatroomList.add(new Chatroom(roomsSet.getLong(1),
                    roomsSet.getString(2), null, null));
        }
        return chatroomList;
    }

    private List<Chatroom> findUsersSocializationRooms(long userUd)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from message where author_id = " + userUd);
        ResultSet roomsSet = preparedStatement.executeQuery();
        List<Chatroom> chatroomList = new ArrayList<>();
        while (roomsSet.next()) {
            chatroomList.add(new Chatroom(roomsSet.getLong(1),
                    roomsSet.getString(2), null, null));
        }
        return chatroomList;
    }
}
