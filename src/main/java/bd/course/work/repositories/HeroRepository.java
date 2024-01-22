package bd.course.work.repositories;

import bd.course.work.entities.Hero;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HeroRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HeroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Hero save(Hero hero) {
        String sql = "INSERT INTO Hero (name, age, current_hp, level_id, user_id, class_id, xp) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, hero.getName(), hero.getLevelId(), hero.getUserId(), hero.getHeroClassId(), hero.getXp());
        return hero; // Возвращаем объект героя
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
