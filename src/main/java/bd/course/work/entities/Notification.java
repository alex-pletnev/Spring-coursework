package bd.course.work.entities;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notification {
    private Long notificationId;
    private Long userId;
    private String message;
    private Timestamp createdAt;
    private boolean read;
}
