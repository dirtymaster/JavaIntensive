package edu.school21.service.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;
    private Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDriver(String driver) {
        ((HikariDataSource) dataSource).setDriverClassName(driver);
    }

    public void setUrl(String url) {
        ((HikariDataSource) dataSource).setJdbcUrl(url);
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
                ? Optional.of(new User(id, resultSet.getString("email")))
                : Optional.empty();
        resultSet.close();
        closeConnection();
        return Optional.empty();
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
                    resultSet.getString("email")));
        }
        resultSet.close();
        closeConnection();
        return userList;
    }

    @Override
    public void save(User entity) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into \"user\" (email)"
                        + " values ('" + entity.getEmail() + "')");
        preparedStatement.execute();
        closeConnection();
    }

    @Override
    public void update(User entity) throws SQLException {
        initConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "update \"user\" set email = " + entity.getEmail()
                        + " where id = " + entity.getId());
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
                ? Optional.of(new User(resultSet.getLong("id"), email))
                : Optional.empty();
        resultSet.close();
        closeConnection();
        return  optionalUser;
    }
}
