package bd.course.work.dto.output;

import lombok.Data;

@Data
public class HeroOutputDTO {
    private Long id;
    private String name;
    private Integer age;
    private Integer currentHp;
    private LevelOutputDTO level;
    private UserOutputDTO user;
    private ClazzOutputDTO clazz;
    private Integer xp;
}