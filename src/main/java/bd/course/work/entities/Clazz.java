package bd.course.work.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;

    private String ability;
}