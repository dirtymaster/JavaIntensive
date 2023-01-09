package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("create table if not exists message (\n"
                + "sender varchar not null,"
                + "text varchar not null,\n"
                + "time timestamp default current_timestamp);");
    }

    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet resultSet, int i) throws SQLException {
            Message message = new Message(resultSet.getString("sender"),
                    resultSet.getString("text"),
                    resultSet.getTimestamp("time"));
            return message;
        }
    }

    @Override
    public Message findById(Long id) {
        System.err.println("Not implemented");
        System.exit(1);
        return null;
    }

    @Override
    public List<Message> findAll() {
        String sql = "select * from message";
        List<Message> messages = jdbcTemplate.query(sql, new MessageRowMapper());
        return messages;
    }

    @Override
    public void save(Message entity) {
        String sql = "insert into message (sender, text) VALUES (?, ?)";
        jdbcTemplate.update(sql, entity.getSender(), entity.getMessage());
    }

    @Override
    public void update(Message entity) {
        System.err.println("Not implemented");
        System.exit(1);
    }

    @Override
    public void delete(Long id) {
        System.err.println("Not implemented");
        System.exit(1);
    }
}
