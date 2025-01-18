package bd.course.work.entities;

import bd.course.work.entities.enums.QuestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Timestamp dueDate;

    @Enumerated(EnumType.STRING)
    private QuestStatus status;

    private int xp;

    @ManyToOne
    private Level minHeroLevel;

    private int damageToHero;

    @ManyToOne
    private Priority priority;

    @ManyToOne
    private Type type;
}