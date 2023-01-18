package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        UsersRepository usersRepository = null;
        Connection connection = null;
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setLoginTimeout(3);
            connection = dataSource.getConnection();
            usersRepository = new UsersRepositoryJdbcImpl(connection);
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database");
            System.exit(1);
        }
        try {
            List<User> userList = usersRepository.findAll(2, 3);
            for (User user : userList) {
                System.out.println(user);
            }
            connection.close();
        } catch (SQLException e) {
            System.err.println("Unable to find users");
            System.exit(1);
        }
    }
}
