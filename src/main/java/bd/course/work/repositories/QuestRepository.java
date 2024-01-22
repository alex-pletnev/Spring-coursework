package bd.course.work.repositories;

import bd.course.work.entities.Quest;
import bd.course.work.util.RowMappers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestRepository {

    private final JdbcTemplate jdbcTemplate;


    public QuestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateQuestStatus(Long questId, String newStatus) {
        String sql = "UPDATE Quests SET status = ? WHERE quest_id = ?";
        jdbcTemplate.update(sql, newStatus, questId);
    }

    public List<Quest> getAllQuests() {
        String sql = "SELECT * FROM Quests";
        return jdbcTemplate.query(sql, RowMappers.getQuestsRowMapper());
    }

    public Optional<Quest> findById(Long questId) {
        String sql = "SELECT * FROM Quests WHERE quest_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{questId}, RowMappers.getQuestsRowMapper()));
    }
}
