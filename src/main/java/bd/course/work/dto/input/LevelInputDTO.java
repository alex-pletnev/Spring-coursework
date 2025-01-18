package bd.course.work.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LevelInputDTO {
    @NotNull(message = "Level value is mandatory")
    @Min(value = 1, message = "Level value must be at least 1")
    private Long value;

    @NotNull(message = "XP to next level is mandatory")
    @Min(value = 0, message = "XP to next level cannot be negative")
    private Integer xpToNextLevel;

    @NotNull(message = "Max HP is mandatory")
    @Min(value = 1, message = "Max HP must be at least 1")
    private Integer maxHp;

    @NotNull(message = "Damage is mandatory")
    @Min(value = 0, message = "Damage cannot be negative")
    private Integer damage;

    @NotNull(message = "Mana is mandatory")
    @Min(value = 0, message = "Mana cannot be negative")
    private Integer mana;
}