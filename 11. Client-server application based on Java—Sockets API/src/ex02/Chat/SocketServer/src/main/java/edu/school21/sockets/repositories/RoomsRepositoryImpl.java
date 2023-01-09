package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class RoomsRepositoryImpl implements RoomsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomsRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        init();
    }

    private void init() {
        jdbcTemplate.execute("create table if not exists room (\n"
                + "id serial primary key\n,"
                + "name varchar unique not null" +
                ");");
    }

    private static class RoomRowMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Room(resultSet.getLong("id"),
                    resultSet.getString("name"));
        }
    }

    @Override
    public Room findById(Long id) {
        String sql = "select * from room where id = ?";
        return jdbcTemplate.query(sql,
                new Object[]{id},
                new int[]{Types.INTEGER},
                new RoomRowMapper()).stream().findAny().orElse(null);
    }

    @Override
    public List<Room> findAll() {
        String sql = "select * from room";
        return jdbcTemplate.query(sql, new RoomRowMapper());
    }

    @Override
    public void save(Room entity) {
        String sql = "insert into room (name) VALUES (?)";
        jdbcTemplate.update(sql, entity.getName());
    }

    @Override
    public void update(Room entity) {
        System.err.println("Not implemented");
        System.exit(1);
    }

    @Override
    public void delete(Long id) {
        System.err.println("Not implemented");
        System.exit(1);
    }

    @Override
    public Room getRoomByName(String name) {
        String sql = "select * from room where name = ?";
        List<Room> rooms = jdbcTemplate.query(sql, new RoomRowMapper(), name);
        return rooms.get(0);
    }
}
