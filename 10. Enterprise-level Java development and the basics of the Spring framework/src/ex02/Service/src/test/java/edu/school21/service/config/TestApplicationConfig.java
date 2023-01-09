package edu.school21.service.config;

import edu.school21.service.repositories.UsersRepositoryJdbcImpl;
import edu.school21.service.repositories.UsersRepositoryJdbcTemplateImpl;
import edu.school21.service.services.UsersService;
import edu.school21.service.services.UsersServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestApplicationConfig {
    @Bean
    DataSource dataSource() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts("schema.sql", "data.sql")
                .build();
        return dataSource;
    }

    @Bean("usersServiceJdbcImpl")
    UsersService usersServiceJdbc(DataSource dataSource) {
        return new UsersServiceImpl(new UsersRepositoryJdbcImpl(dataSource));
    }

    @Bean("usersServiceJdbcTemplateImpl")
    UsersService usersServiceJdbcTemplate(DataSource dataSource) {
        return new UsersServiceImpl(new UsersRepositoryJdbcTemplateImpl(dataSource));
    }
}
