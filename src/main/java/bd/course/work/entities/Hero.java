package bd.course.work.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private int currentHp;

    @ManyToOne
    private Level level;

    @ManyToOne
    private User user;

    @ManyToOne
    private Clazz clazz;

    private int xp;
}