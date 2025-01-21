package bd.course.work.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillboardInputDTO {
    /**
     * Identifier of the type.
     * Usually null when creating a new record.
     */
    private Long id;

    @NotNull(message = "Hero is mandatory")
    private HeroInputDTO hero;

    @NotNull(message = "Quest is mandatory")
    private QuestInputDTO quest;

    private boolean result;

    private String at;
}