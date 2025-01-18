package bd.course.work.dto.output;

import lombok.Data;

@Data
public class NotificationOutputDTO {
    private Long id;
    private UserOutputDTO user;
    private String content;
    private String createdAt;
    private boolean isRead;
}