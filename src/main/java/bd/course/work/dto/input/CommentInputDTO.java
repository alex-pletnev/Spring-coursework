package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentInputDTO {
    @NotNull(message = "Quest is mandatory")
    private QuestInputDTO quest;

    @NotNull(message = "User is mandatory")
    private UserInputDTO user;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private String at;
}