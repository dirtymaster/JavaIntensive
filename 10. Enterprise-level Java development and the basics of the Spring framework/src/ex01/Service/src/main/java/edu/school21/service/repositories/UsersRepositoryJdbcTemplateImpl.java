package edu.school21.service.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.service.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDriver(String driver) {
        ((DriverManagerDataSource) dataSource).setDriverClassName(driver);
    }

    public void setUrl(String url) {
        ((DriverManagerDataSource) dataSource).setUrl(url);
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
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "select * from \"user\"";
        List<User> users = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getLong("id"), rs.getString("email"));
                return user;
            }
        });
        return users;
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "insert into \"user\" (email) values (?)";
        jdbcTemplate.update(sql, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.setDataSource(dataSource);
        String sql = "update \"user\" set email = ? where id = ?";
        jdbcTemplate.update(sql, entity.getEmail(), entity.getId());
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
        return Optional.of(user);
    }
}
