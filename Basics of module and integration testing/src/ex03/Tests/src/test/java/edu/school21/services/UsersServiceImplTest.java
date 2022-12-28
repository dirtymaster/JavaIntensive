package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

public class UsersServiceImplTest {
    @Mock
    UsersRepository usersRepository;

    public UsersServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticateShouldReturnTrue() {
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        given(usersRepository.findByLogin("aboba")).willReturn(
                new User(1, "aboba", "Govnoz@vr666", false));
        Assertions.assertTrue(
                usersService.authenticate("aboba", "Govnoz@vr666"));
    }

    @Test
    public void authenticateShouldReturnFalse() {
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        given(usersRepository.findByLogin("hacker"))
                .willThrow(UsersRepository.EntityNotFoundException.class);
        Assertions.assertFalse(usersService.authenticate("hacker", "vzlom"));

        given(usersRepository.findByLogin("admin")).willReturn(
                new User(2, "admin", "admin", false));
        Assertions.assertFalse(usersService.authenticate("admin", "vzlom"));
    }

    @Test
    public void authenticateShouldThrowException() {
        UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);
        given(usersRepository.findByLogin("aboba")).willReturn(
                new User(1, "aboba", "Govnoz@vr666", true));
        Assertions.assertThrows(
                AlreadyAuthenticatedException.class,
                () -> usersService.authenticate("aboba", "Govnoz@vr666"));
    }
}
