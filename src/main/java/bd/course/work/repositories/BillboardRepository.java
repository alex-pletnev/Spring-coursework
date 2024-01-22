package bd.course.work.repositories;

import bd.course.work.entities.Billboard;
import bd.course.work.util.RowMappers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillboardRepository {

    private final JdbcTemplate jdbcTemplate;


    public BillboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addBillboard(Billboard billboard) {
        String sql = "INSERT INTO Billboard (hero_id, quests_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, billboard.getHeroId(), billboard.getQuestId());
    }

    public List<Billboard> findAll() {
        String sql = "SELECT * FROM Billboard";
        return jdbcTemplate.query(sql, RowMappers.getBillboardRowMapper());
    }

    public void updateBillboard(Billboard billboard) {
        String sql = "UPDATE Billboard SET quests_id = ? WHERE hero_id = ?";
        jdbcTemplate.update(sql, billboard.getQuestId(), billboard.getHeroId());
    }

    public void deleteBillboard(int heroId, int questsId) {
        String sql = "DELETE FROM Billboard WHERE hero_id = ? AND quests_id = ?";
        jdbcTemplate.update(sql, heroId, questsId);
    }

    public List<Billboard> getAllByHeroId(Long heroId) {
        String sql = "SELECT * FROM Billboard WHERE hero_id = ?";
        return jdbcTemplate.query(sql, new Object[]{heroId}, RowMappers.getBillboardRowMapper());
    }


}
