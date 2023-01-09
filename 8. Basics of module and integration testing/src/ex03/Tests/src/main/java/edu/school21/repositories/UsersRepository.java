package edu.school21.repositories;

import edu.school21.models.User;

public interface UsersRepository {
    class EntityNotFoundException extends RuntimeException {
    }

    User findByLogin(String login);

    void update(User user);
}
