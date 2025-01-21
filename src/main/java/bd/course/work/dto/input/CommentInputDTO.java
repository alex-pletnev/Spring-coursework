package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentInputDTO {
    /**
     * Identifier of the type.
     * Usually null when creating a new record.
     */
    private Long id;

    @NotNull(message = "Quest is mandatory")
    private QuestInputDTO quest;

    @NotNull(message = "User is mandatory")
    private UserInputDTO user;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private String at;
}