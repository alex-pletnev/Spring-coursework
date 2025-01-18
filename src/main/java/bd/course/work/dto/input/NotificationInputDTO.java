package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationInputDTO {
    @NotNull(message = "User is mandatory")
    private UserInputDTO user;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private boolean isRead;
}