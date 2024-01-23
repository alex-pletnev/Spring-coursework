package bd.course.work.repositories;

import bd.course.work.dto.CommentDTO;
import bd.course.work.entities.Comment;
import bd.course.work.util.RowMappers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Comment> addComment(CommentDTO commentDTO) {
        String sql = "INSERT INTO Comments (quest_id, user_id, comment_text, timestamp) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var timestamp = new Timestamp(System.currentTimeMillis());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Math.toIntExact(commentDTO.questId()));
            ps.setInt(2, Math.toIntExact(commentDTO.userId()));
            ps.setString(3, commentDTO.commentText());
            ps.setTimestamp(4, timestamp);
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
    }

    public List<Comment> findAllByQuestId(Long questId) {
        String sql = "SELECT * FROM Comments WHERE quest_id = ?";
        return jdbcTemplate.query(sql, new Object[]{questId}, RowMappers.getCommentsRowMapper());
    }

    public Optional<Comment> findById(Long commentId) {
        String sql = "SELECT * FROM comments WHERE comment_id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{commentId}, RowMappers.getCommentsRowMapper()));
    }


}

