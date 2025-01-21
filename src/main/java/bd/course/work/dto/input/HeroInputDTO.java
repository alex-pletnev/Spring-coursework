package bd.course.work.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HeroInputDTO {
    /**
     * Identifier of the type.
     * Usually null when creating a new record.
     */
    private Long id;

    @NotBlank(message = "Hero name is mandatory")
    @Size(max = 255, message = "Hero name cannot exceed 255 characters")
    private String name;

    @NotNull(message = "Hero age is mandatory")
    @Min(value = 1, message = "Hero age must be at least 1")
    private Integer age;

    @NotNull(message = "Current HP is mandatory")
    @PositiveOrZero(message = "Current HP cannot be negative")
    private Integer currentHp;

    @NotNull(message = "Level is mandatory")
    private LevelInputDTO level;

    @NotNull(message = "User is mandatory")
    private UserInputDTO user;

    @NotNull(message = "Class is mandatory")
    private ClazzInputDTO clazz;

    @NotNull(message = "XP is mandatory")
    @PositiveOrZero(message = "XP cannot be negative")
    private Integer xp;
}