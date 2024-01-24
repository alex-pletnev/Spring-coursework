package bd.course.work.repositories;

import bd.course.work.dto.HeroDTO;
import bd.course.work.entities.Hero;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class HeroRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HeroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Hero> save(HeroDTO heroDTO) {
        String sql = "INSERT INTO Hero (name, age, level_id, user_id, class_id, xp) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, heroDTO.name());
            ps.setInt(2, 0);
            ps.setInt(3, 1);
            ps.setInt(4, Math.toIntExact(heroDTO.userId()));
            ps.setInt(5, Math.toIntExact(heroDTO.heroClassId()));
            ps.setInt(6, 0);
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            // Получение полной сущности из базы данных
            return findById(key.longValue());
        } else {
            // Обработка ситуации, когда ключ не был сгенерирован
            throw new RuntimeException("Failed to retrieve the generated key for the notification.");
        }
        // Возвращаем объект героя
    }

    public void deleteById(Long heroId) {
        String sql = "DELETE FROM Hero WHERE hero_id = ?";
        jdbcTemplate.update(sql, heroId);
    }

    public void deleteByUserId(Long heroId) {
        String sql = "DELETE FROM Hero WHERE user_id = ?";
        jdbcTemplate.update(sql, heroId);
    }

    public Optional<Hero> findById(Long heroId) {
        String sql = "SELECT * FROM Hero WHERE hero_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{heroId}, RowMappers.getHeroRowMapper()));
    }

    public Optional<Hero> findByUserId(Long userId) {
        String sql = "SELECT * FROM Hero WHERE hero_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{userId}, RowMappers.getHeroRowMapper()));
    }

    public void updateHero(Hero hero) {
        String sql = "UPDATE Hero SET name = ?, age = ?, current_hp = ?, level_id = ?, user_id = ?, class_id = ?, xp = ? WHERE hero_id = ?";
        jdbcTemplate.update(sql, hero.getName(), hero.getAge(), hero.getCurrentHp(), hero.getLevelId(), hero.getUserId(), hero.getHeroClassId(), hero.getHeroId());
    }
}
