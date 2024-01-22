package bd.course.work.repositories;

import bd.course.work.entities.Comment;
import bd.course.work.util.RowMappers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public CommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addComment(Comment comment) {
        String sql = "INSERT INTO Comments (quest_id, user_id, comment_text, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, comment.getQuestId(), comment.getUserId(), comment.getCommentText(), comment.getTimestamp());
    }

    public List<Comment> findAllByQuestId(Long questId) {
        String sql = "SELECT * FROM Comments WHERE quest_id = ?";
        return jdbcTemplate.query(sql, new Object[]{questId}, RowMappers.getCommentsRowMapper());
    }


}

