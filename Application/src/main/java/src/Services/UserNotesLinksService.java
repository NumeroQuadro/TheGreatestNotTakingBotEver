package src.Services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.UsersNotesLinks;
import src.Repositories.UserNotesLinksRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserNotesLinksService {
    private final UserNotesLinksRepository userNotesLinksRepository;
    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public UserNotesLinksService(
            UserNotesLinksRepository userNotesLinksRepository,
            NoteService noteService,
            UserService userService
    ) {
        this.userNotesLinksRepository = userNotesLinksRepository;
        this.noteService = noteService;
        this.userService = userService;
    }

    public UsersNotesLinks addUserWithNote(UUID userId, UUID noteId) {
        var userNote = new UsersNotesLinks();
        var note = noteService.getNote(noteId).orElseThrow(
                () -> new EntityNotFoundException("Note not found with id: " + noteId));
        var user = userService.getUser(userId).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + userId));

        userNote.setNote(note);
        userNote.setUser(user);

        return userNotesLinksRepository.save(userNote);
    }

    public List<UsersNotesLinks> getNotesRelatedWithUserById(UUID userId) {
        return userNotesLinksRepository.findALlByUserId(userId);
    }
}
