package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UsersRepositoryImpl implements UsersRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("create table if not exists \"user\" (\n" +
                "id serial primary key,\n" +
                "username varchar not null unique,\n" +
                "encrypted_password varchar not null);");
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            return new User(rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("encrypted_password"));
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM \"user\" WHERE id = ? limit 1";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper, id);

        return user != null ? Optional.of(user) : Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO \"user\" "
                + "(username, encrypted_password) VALUES (?, ?)";
        jdbcTemplate.update(sql, entity.getUsername(),
                entity.getEncryptedPassword());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE \"user\" SET username = ?, "
                + "encrypted_password = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getUsername(),
                entity.getEncryptedPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM \"user\" WHERE username = ? limit 1";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper);
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
