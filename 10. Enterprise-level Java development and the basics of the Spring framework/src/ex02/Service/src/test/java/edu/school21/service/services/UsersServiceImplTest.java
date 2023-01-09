package edu.school21.service.services;

import edu.school21.service.config.TestApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UsersServiceImplTest {
//    private DataSource dataSource;
    private final UsersService usersServiceJdbcImpl;
    private final UsersService usersServiceJdbcTemplateImpl;

    public UsersServiceImplTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                TestApplicationConfig.class);
//        dataSource = context.getBean("dataSource", DataSource.class);
        usersServiceJdbcImpl
                = context.getBean("usersServiceJdbcImpl", UsersService.class);
        usersServiceJdbcTemplateImpl = context.getBean(
                "usersServiceJdbcTemplateImpl", UsersService.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aboba@gmail.com", "bbb@mail.ru", "ggg@yandex.ru"})
    public void resultShouldNotBeNull1(String email) throws SQLException {
        Assertions.assertNotNull(usersServiceJdbcImpl.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"fff@gmail.com", "sss@mail.ru", "eee@yandex.ru"})
    public void resultShouldNotBeNull2(String email) throws SQLException {
        Assertions.assertNotNull(usersServiceJdbcTemplateImpl.signUp(email));
    }
}
