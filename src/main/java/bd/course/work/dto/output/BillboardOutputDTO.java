package bd.course.work.dto.output;

import lombok.Data;

@Data
public class BillboardOutputDTO {
    private Long id;
    private HeroOutputDTO hero;
    private QuestOutputDTO quest;
    private boolean result;
    private String at;
}