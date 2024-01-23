package bd.course.work.controllers;

import bd.course.work.dto.CommentDTO;
import bd.course.work.entities.Comment;
import bd.course.work.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{questId}")
    public ResponseEntity<List<Comment>> getAllCommentsByQuestId(@PathVariable Long questId) {
        return ResponseEntity.ok(commentService.getAllCommentsByQuestId(questId));
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
