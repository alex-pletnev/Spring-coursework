package bd.course.work.repositories;

import bd.course.work.entities.Level;
import bd.course.work.util.RowMappers;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LevelRepository {
    private final JdbcTemplate jdbcTemplate;

    public LevelRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addLevel(Level level) {
        String sql = "INSERT INTO Level (xp, hp, damage, mana) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, level.getXp(), level.getHp(), level.getDamage(), level.getMana());
    }

    public List<Level> findAll() {
        String sql = "SELECT * FROM Level";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Level.class));
    }

    public void updateLevel(Level level) {
        String sql = "UPDATE Level SET xp = ?, hp = ?, damage = ?, mana = ? WHERE level_id = ?";
        jdbcTemplate.update(sql, level.getXp(), level.getHp(), level.getDamage(), level.getMana(), level.getLevelId());
    }

    public void deleteLevel(int levelId) {
        String sql = "DELETE FROM Level WHERE level_id = ?";
        jdbcTemplate.update(sql, levelId);
    }

    public Optional<Level> findById(Long levelId) {
        String sql = "SELECT * FROM public.level WHERE level_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{levelId}, RowMappers.getLevelRowMapper()));
    }
}

