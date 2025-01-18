package bd.course.work.dto.output;

import lombok.Data;

@Data
public class QuestOutputDTO {
    private Long id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private Integer xp;
    private Integer damageToHero;
    private LevelOutputDTO minHeroLevel;
    private PriorityOutputDTO priority;
    private TypeOutputDTO type;
}