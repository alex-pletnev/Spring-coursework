package bd.course.work.repositories;

import bd.course.work.entities.User;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public User findByUsernameAndPassword(String userName, String password) {
        String sql = "SELECT * FROM \"User\" WHERE user_name = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName, password}, RowMappers.getUserRowMapper());
    }

    public boolean existsByUsername(String userName) {
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE user_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userName}, Integer.class);
        return count != null && count > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
        return count != null && count > 0;
    }

    public Optional<User> findByUsername(String userName) {
        String sql = "SELECT * FROM \"User\" WHERE user_name = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{userName}, RowMappers.getUserRowMapper()));
    }

    public User save(User user) {
        String sqlInsert = "INSERT INTO \"User\" (user_name, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsert, user.getUsername(), user.getPassword(), user.getEmail());

        String sqlSelect = "SELECT * FROM \"User\" WHERE user_name = ? AND email = ?";
        return jdbcTemplate.queryForObject(sqlSelect, new Object[]{user.getUsername(), user.getEmail()}, RowMappers.getUserRowMapper());
    }

    public void createUser(User user) {
        String sql = "INSERT INTO \"User\" (user_name, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User readUser(Long userId) {
        String sql = "SELECT * FROM \"User\" WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, RowMappers.getUserRowMapper());
    }

    public void updateUser(User user) {
        String sql = "UPDATE \"User\" SET user_name = ?, password = ?, email = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getUserId());
    }

    public void deleteUser(Long userId) {
        String sql = "DELETE FROM \"User\" WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public List<User> findAllUsers() {
        String sql = "SELECT * FROM \"User\"";
        return jdbcTemplate.query(sql, RowMappers.getUserRowMapper());
    }
}
