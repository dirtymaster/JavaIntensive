package edu.school21.service.application;

import edu.school21.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                "edu.school21.service");
        UsersRepository usersRepository
                = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());

        usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl",
                UsersRepository.class);
        System.out.println(usersRepository.findAll());
    }
}
