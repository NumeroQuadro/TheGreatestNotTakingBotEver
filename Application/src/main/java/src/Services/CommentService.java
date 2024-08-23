package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Comment;
import src.Repositories.CommentRepository;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Add new comment
     * @param textMessage
     * @param timecode
     * @return src.Models.Comment
     */
    public Comment addComment(String textMessage, Long timecode) {
        var comment = new Comment();
        comment.setTimecode(timecode);
        comment.setTextMessage(textMessage);

        return commentRepository.save(comment);
    }

    public Comment addComment(String textMessage, Integer page) {
        var comment = new Comment();
        comment.setTextMessage(textMessage);
        comment.setPage(page);

        return commentRepository.save(comment);
    }

    /**
     * Set new text message to existing comment (which is found by its id)
     * @param commentId
     * @param newTextMessage
     * @return src.Models.Comment
     * @throws java.util.NoSuchElementException if no such comment exist
     */
    public Comment setTextMessageToComment(Integer commentId, String newTextMessage) {
        var comment = commentRepository.findById(commentId).orElseThrow();
        comment.setTextMessage(newTextMessage);

        return commentRepository.save(comment);
    }

    /**
     * Set new timecode to existing comment (which is found by its id)
     * @param commendId
     * @param timecode
     * @throws java.util.NoSuchElementException if no such comment exist
     */
    public Comment setTimecodeToComment(Integer commendId, Long timecode) {
        var comment = commentRepository.findById(commendId).orElseThrow();
        comment.setTimecode(timecode);

        return commentRepository.save(comment);
    }

    /**
     * Set new page number to existing comment (which is found by its id)
     * @param commentId
     * @param newPage
     * @throws java.util.NoSuchElementException if no such comment exist
     */
    public Comment setPageToComment(Integer commentId, Integer newPage) {
        var comment = commentRepository.findById(commentId).orElseThrow();
        comment.setPage(newPage);

        return commentRepository.save(comment);
    }

    /**
     * Get comment by its id
     * @param commentId
     * @return src.Models.Comment
     * @throws java.util.NoSuchElementException - if no such comment exist
     */
    public Comment getComment(Integer commentId) {
        return commentRepository.findById(commentId).orElseThrow();
    }

    public void deleteComment(Integer commentId) {
        var comment = commentRepository.findById(commentId);
        comment.ifPresent(comm -> commentRepository.delete(comm));
    }
}
