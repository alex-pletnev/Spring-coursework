package bd.course.work.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double priority;

    private String name;
}