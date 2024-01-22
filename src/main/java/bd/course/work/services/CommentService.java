package bd.course.work.services;

import bd.course.work.entities.Comment;
import bd.course.work.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllCommentsByQuestId(Long questId) {
        return commentRepository.findAllByQuestId(questId);
    }

    @Transactional
    public Comment addComment(Comment comment) {
        commentRepository.addComment(comment);
        return comment;
    }
}
