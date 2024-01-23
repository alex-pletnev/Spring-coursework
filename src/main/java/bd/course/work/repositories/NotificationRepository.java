package bd.course.work.repositories;

import bd.course.work.dto.NotificationDTO;
import bd.course.work.entities.Notification;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NotificationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Notification> findAllNotificationsForUser(Long userId) {
        String sql = "SELECT * FROM Notifications WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, RowMappers.getNotificationsRowMapper());
    }

    public Optional<Notification> addNotification(NotificationDTO notificationDTO) {
        String sql = "INSERT INTO Notifications (user_id, message, created_at, read) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var timestamp = new Timestamp(System.currentTimeMillis());

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, Math.toIntExact(notificationDTO.userId()));
            ps.setString(2, notificationDTO.message());
            ps.setTimestamp(3, timestamp);
            ps.setBoolean(4, false);
            return ps;
        }, keyHolder);

        // Предположим, что notification_id это автоинкрементное поле
        Number key = keyHolder.getKey();
        if (key != null) {
            // Получение полной сущности из базы данных
            return findById(key.longValue());
        } else {
            // Обработка ситуации, когда ключ не был сгенерирован
            throw new RuntimeException("Failed to retrieve the generated key for the notification.");
        }
    }

    public void updateNotification(Notification notification) {
        String sql = "UPDATE Notifications SET user_id = ?, message = ?, created_at = ?, read = ? WHERE notification_id = ?";
        jdbcTemplate.update(sql, notification.getUserId(), notification.getMessage(), notification.getCreatedAt(), notification.isRead(), notification.getNotificationId());
    }

    public Optional<Notification> findById(Long notificationId) {
        String sql = "SELECT * FROM Notifications WHERE notification_id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{notificationId}, RowMappers.getNotificationsRowMapper()));
    }

}