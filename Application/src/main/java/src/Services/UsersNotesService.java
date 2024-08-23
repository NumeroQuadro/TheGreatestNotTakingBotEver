package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.UsersNotes;
import src.Repositories.UsersNotesRepository;

@Service
public class UsersNotesService {
    private UsersNotesRepository usersNotesRepository;

    @Autowired
    public UsersNotesService(UsersNotesRepository usersNotesRepository) {
        this.usersNotesRepository = usersNotesRepository;
    }

    public UsersNotes addUserWithNote(Integer userId, Integer noteId) {
        var userNote = new UsersNotes();
        userNote.setUserId(userId);
        userNote.setNoteId(noteId);

        return usersNotesRepository.save(userNote);
    }
}
