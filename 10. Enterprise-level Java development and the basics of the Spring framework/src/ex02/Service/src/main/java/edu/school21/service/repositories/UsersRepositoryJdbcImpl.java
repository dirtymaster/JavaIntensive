package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;
    private Connection connection;

    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void initConnection() throws SQLException {
        connection = dataSource.getConnection();
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"user\" where id = " + id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<User> optionalUser = resultSet.next()
                ? Optional.of(new User(id,
                resultSet.getString("email"),
                resultSet.getString("password")))
                : Optional.empty();
        resultSet.close();
        closeConnection();
        return optionalUser;
    }

    @Override
    public List<User> findAll() throws SQLException {
        initConnection();
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"user\"");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            userList.add(new User(resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")));
        }
        resultSet.close();
        closeConnection();
        return userList;
    }

    @Override
    public void save(User entity) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into \"user\" (email, password)"
                        + " values ('" + entity.getEmail() + "', '"
                        + entity.getPassword() + "')");
        preparedStatement.execute();
        closeConnection();
    }

    @Override
    public void update(User entity) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update \"user\" set email = '" + entity.getEmail()
                        + "', password = '" + entity.getPassword()
                        + "' where id = " + entity.getId());
        preparedStatement.execute();
        closeConnection();
    }

    @Override
    public void delete(Long id) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from \"user\" where id = " + id);
        preparedStatement.execute();
        closeConnection();
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from \"user\" where email = '" + email +
                        "' limit 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<User> optionalUser = resultSet.next()
                ? Optional.of(new User(resultSet.getLong("id"),
                email, resultSet.getString("password")))
                : Optional.empty();
        resultSet.close();
        closeConnection();
        return optionalUser;
    }
}
