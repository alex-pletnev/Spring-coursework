package bd.course.work.dto.output;

import lombok.Data;

@Data
public class LevelOutputDTO {
    private Long id;
    private Long value;
    private Integer xpToNextLevel;
    private Integer maxHp;
    private Integer damage;
    private Integer mana;
}