package src.Services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Comment;
import src.Repositories.CommentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository
    ) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(String textMessage, Long timecode, Integer page) {
        var comment = new Comment();
        comment.setTimecode(timecode);
        comment.setPage(page);
        comment.setTextMessage(textMessage);

        return commentRepository.save(comment);
    }

    @Transactional
    public Optional<Comment> updateComment(UUID commentId, Comment updatedComment) {
        return commentRepository.findById(commentId).map(comment -> {
            comment.setId(updatedComment.getId());
            comment.setTextMessage(updatedComment.getTextMessage());
            comment.setTimecode(updatedComment.getTimecode());

            return comment;
        });
    }

    public Optional<Comment> getComment(UUID commentId) {
        return commentRepository.findById(commentId);
    }

    public void deleteComment(UUID commentId) {
        var comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Map<UUID, Comment> getCommentsByIds(List<UUID> commentIds) {
        List<Comment> comments = commentRepository.findAllById(commentIds);
        return comments.stream().collect(Collectors.toMap(Comment::getId, comment -> comment));
    }
}
