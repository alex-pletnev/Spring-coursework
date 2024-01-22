package bd.course.work.repositories;

import bd.course.work.entities.Notification;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void addNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (user_id, message, created_at, read) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, notification.getUserId(), notification.getMessage(), notification.getCreatedAt(), notification.isRead());
    }

    public void updateNotification(Notification notification) {
        String sql = "UPDATE Notifications SET user_id = ?, message = ?, created_at = ?, read = ? WHERE notification_id = ?";
        jdbcTemplate.update(sql, notification.getUserId(), notification.getMessage(), notification.getCreatedAt(), notification.isRead(), notification.getNotificationId());
    }

}