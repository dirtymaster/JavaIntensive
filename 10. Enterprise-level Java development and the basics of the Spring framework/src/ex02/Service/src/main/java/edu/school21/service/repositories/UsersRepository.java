package edu.school21.service.repositories;

import edu.school21.service.models.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email) throws SQLException;
}
