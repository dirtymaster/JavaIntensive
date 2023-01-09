package edu.school21.service.repositories;

import edu.school21.service.models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "select * from \"user\" where id = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> findAll() {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "select * from \"user\"";
        List<User> users = jdbcTemplate.query(sql, new Object[]{}, (rs, rowNum) -> {
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"));
            return user;
        });
        return users;
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "insert into \"user\" (email, password) values (?, ?)";
        jdbcTemplate.update(sql, entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "update \"user\" set email = ?, password = ? where id = ?";
        jdbcTemplate.update(
                sql, entity.getEmail(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "delete from \"user\" where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "select * from \"user\"" +
                "where email = '" + email + "' limit 1";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user;
        try {
            user = jdbcTemplate.queryForObject(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
