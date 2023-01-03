package edu.school21.dao;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    private static DAO instance = null;

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    private Connection connection;

    private DAO() {
        try {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setLoginTimeout(3);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database");
            System.exit(1);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
