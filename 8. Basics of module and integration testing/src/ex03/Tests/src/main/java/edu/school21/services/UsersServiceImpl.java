package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    boolean authenticate(String login, String password) {
        User user;
        try {
            user = usersRepository.findByLogin(login);
        } catch (Exception e) {
            return false;
        }
        if (user.getPassword().equals(password)) {
            if (user.isAuthenticationSuccessStatus()) {
                throw new AlreadyAuthenticatedException();
            }
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}
