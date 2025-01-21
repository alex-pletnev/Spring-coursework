package bd.course.work.controllers;

import bd.course.work.dto.input.CommentInputDTO;
import bd.course.work.dto.output.CommentOutputDTO;
import bd.course.work.services.CommentService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления комментариями.
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Создание нового комментария.
     *
     * @param commentInputDTO DTO с данными нового комментария.
     * @return Созданный комментарий в виде DTO.
     */
    @PostMapping
    public ResponseEntity<CommentOutputDTO> createComment(@Valid @RequestBody CommentInputDTO commentInputDTO) {
        LOGGER.info("Request to create comment for quest: {}", commentInputDTO.getQuest().getTitle());
        CommentOutputDTO createdComment = commentService.createComment(commentInputDTO);
        LOGGER.info("Comment created with ID: {}", createdComment.getId());
        return ResponseEntity.ok(createdComment);
    }

    /**
     * Получение всех комментариев.
     *
     * @return Список всех комментариев в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<CommentOutputDTO>> getAllComments() {
        LOGGER.info("Request to fetch all comments");
        List<CommentOutputDTO> comments = commentService.getAllComments();
        LOGGER.info("Fetched {} comments", comments.size());
        return ResponseEntity.ok(comments);
    }

    /**
     * Получение комментария по идентификатору.
     *
     * @param id Идентификатор комментария.
     * @return Найденный комментарий в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentOutputDTO> getCommentById(@PathVariable Long id) {
        LOGGER.info("Request to fetch comment by ID: {}", id);
        CommentOutputDTO comment = commentService.getCommentById(id);
        LOGGER.info("Comment found with ID: {}", comment.getId());
        return ResponseEntity.ok(comment);
    }

    /**
     * Обновление комментария.
     *
     * @param id              Идентификатор комментария.
     * @param commentInputDTO DTO с обновлёнными данными.
     * @return Обновлённый комментарий в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentOutputDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentInputDTO commentInputDTO) {
        LOGGER.info("Request to update comment with ID: {}", id);
        CommentOutputDTO updatedComment = commentService.updateComment(id, commentInputDTO);
        LOGGER.info("Comment updated with ID: {}", updatedComment.getId());
        return ResponseEntity.ok(updatedComment);
    }

    /**
     * Удаление комментария.
     *
     * @param id Идентификатор комментария.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        LOGGER.info("Request to delete comment with ID: {}", id);
        commentService.deleteComment(id);
        LOGGER.info("Comment deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}