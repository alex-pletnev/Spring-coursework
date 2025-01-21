package bd.course.work.services;

import bd.course.work.dto.input.CommentInputDTO;
import bd.course.work.dto.output.CommentOutputDTO;
import bd.course.work.entities.Comment;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.CommentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Сервис для управления комментариями.
 */
@Service
public class CommentService {

    private static final Logger LOGGER = LogManager.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final QuestService questService;

    public CommentService(CommentRepository commentRepository, UserService userService, QuestService questService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.questService = questService;
    }

    public CommentOutputDTO createComment(CommentInputDTO commentInputDTO) {
        LOGGER.debug("Creating comment for quest: {}", commentInputDTO.getQuest().getTitle());
        Comment comment = mapToEntity(commentInputDTO);
        Comment savedComment = commentRepository.save(comment);
        return mapToOutputDTO(savedComment);
    }

    public List<CommentOutputDTO> getAllComments() {
        LOGGER.debug("Fetching all comments");
        return commentRepository.findAll().stream()
                .map(this::mapToOutputDTO)
                .toList();
    }

    public CommentOutputDTO getCommentById(Long id) {
        LOGGER.debug("Fetching comment by ID: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id));
        return mapToOutputDTO(comment);
    }

    public CommentOutputDTO updateComment(Long id, CommentInputDTO commentInputDTO) {
        LOGGER.debug("Updating comment with ID: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id));
        comment.setContent(commentInputDTO.getContent());
        comment.setQuest(questService.getQuestEntityById(commentInputDTO.getQuest().getId()));
        comment.setUser(userService.getUserEntityById(commentInputDTO.getUser().getId()));
        comment.setAt(Timestamp.valueOf(commentInputDTO.getAt()));
        Comment updatedComment = commentRepository.save(comment);
        return mapToOutputDTO(updatedComment);
    }

    public void deleteComment(Long id) {
        LOGGER.debug("Deleting comment with ID: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id));
        commentRepository.delete(comment);
    }

    private Comment mapToEntity(CommentInputDTO commentInputDTO) {
        Comment comment = new Comment();
        comment.setContent(commentInputDTO.getContent());
        comment.setQuest(questService.getQuestEntityById(commentInputDTO.getQuest().getId()));
        comment.setUser(userService.getUserEntityById(commentInputDTO.getUser().getId()));
        comment.setAt(commentInputDTO.getAt() != null ? Timestamp.valueOf(commentInputDTO.getAt()) : new Timestamp(System.currentTimeMillis()));
        return comment;
    }

    private CommentOutputDTO mapToOutputDTO(Comment comment) {
        CommentOutputDTO dto = new CommentOutputDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setAt(comment.getAt().toString());
        dto.setQuest(QuestService.mapToOutputDTO(comment.getQuest()));
        dto.setUser(UserService.mapToOutputDTO(comment.getUser()));
        return dto;
    }
}