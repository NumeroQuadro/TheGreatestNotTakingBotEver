package src.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.NoteComments;
import src.Repositories.NoteCommentsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteCommentsService {
    private final NoteCommentsRepository noteCommentsRepository;
    private final CommentService commentService;
    private final ContentDetailsService contentDetailsService;

    @Autowired
    public NoteCommentsService(
            NoteCommentsRepository noteCommentsRepository,
            CommentService commentService,
            ContentDetailsService contentDetailsService
    ) {
        this.noteCommentsRepository = noteCommentsRepository;
        this.commentService = commentService;
        this.contentDetailsService = contentDetailsService;
    }

    public NoteComments addNoteComments(UUID commentId, UUID contentDetailsId) {
        var comment = commentService.getComment(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        var contentDetails = contentDetailsService.getContentDetails(contentDetailsId)
                .orElseThrow(() -> new EntityNotFoundException("Content details not found with id: " + contentDetailsId));

        var noteComments = new NoteComments();
        noteComments.setComment(comment);
        noteComments.setContentDetails(contentDetails);

        return noteCommentsRepository.save(noteComments);
    }

    public List<NoteComments> getNoteCommentsByContentDetailsId(UUID contentDetailsId) {
        return noteCommentsRepository.findAllByContentDetailsId(contentDetailsId);
    }

    public Optional<NoteComments> getNoteCommentById(UUID commentId) {
        return noteCommentsRepository.findById(commentId);
    }

    public List<NoteComments> getNoteCommentsByCommentId(UUID commentId) {
        return noteCommentsRepository.findByCommentId(commentId);
    }
}