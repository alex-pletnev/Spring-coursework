package bd.course.work.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long commentId;
    private Long questId;
    private Long userId;
    private String commentText;
    private LocalDateTime timestamp;
}
