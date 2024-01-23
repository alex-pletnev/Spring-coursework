package bd.course.work.dto;

public record CommentDTO(Long questId,
                         Long userId,
                         String commentText) {
}
