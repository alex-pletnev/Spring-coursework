package bd.course.work.dto.output;

import lombok.Data;

@Data
public class CommentOutputDTO {
    private Long id;
    private QuestOutputDTO quest;
    private UserOutputDTO user;
    private String content;
    private String at;
}