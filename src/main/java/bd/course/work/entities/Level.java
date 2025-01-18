package bd.course.work.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "level", uniqueConstraints = @UniqueConstraint(columnNames = "value"))
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long value;

    @Column(nullable = false)
    private int xpToNextLevel;

    @Column(nullable = false)
    private int maxHp;

    @Column(nullable = false)
    private int damage;

    @Column(nullable = false)
    private int mana;
}