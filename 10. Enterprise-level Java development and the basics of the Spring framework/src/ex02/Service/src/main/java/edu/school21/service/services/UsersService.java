package edu.school21.service.services;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

public interface UsersService {
    String signUp(String email) throws SQLException;
}
