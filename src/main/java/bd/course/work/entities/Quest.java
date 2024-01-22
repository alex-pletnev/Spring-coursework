package bd.course.work.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Quest {
    private Long questId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private int xp;
    private Long minHeroLevelId;
    private int damageToHero;
    private Long priorityId;
    private Long typeId;
}