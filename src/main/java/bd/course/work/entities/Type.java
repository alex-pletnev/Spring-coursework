package bd.course.work.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double difficultyFactor;
}