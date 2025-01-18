package bd.course.work.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestInputDTO {
    @NotBlank(message = "Quest title is mandatory")
    @Size(max = 255, message = "Quest title cannot exceed 255 characters")
    private String title;

    @NotBlank(message = "Quest description is mandatory")
    private String description;

    @NotNull(message = "Due date is mandatory")
    private String dueDate;

    @NotNull(message = "XP is mandatory")
    @PositiveOrZero(message = "XP cannot be negative")
    private Integer xp;

    @NotNull(message = "Damage to hero is mandatory")
    @PositiveOrZero(message = "Damage to hero cannot be negative")
    private Integer damageToHero;

    @NotNull(message = "Minimum hero level is mandatory")
    private LevelInputDTO minHeroLevel;

    @NotNull(message = "Priority is mandatory")
    private PriorityInputDTO priority;

    @NotNull(message = "Type is mandatory")
    private TypeInputDTO type;
}