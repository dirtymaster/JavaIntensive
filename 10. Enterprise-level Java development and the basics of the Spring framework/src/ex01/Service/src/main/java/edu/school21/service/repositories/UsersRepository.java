package edu.school21.service.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import edu.school21.service.models.User;

public interface UsersRepository extends CrudRepository<User> {
    public Optional<User> findByEmail(String email) throws SQLException;
}
