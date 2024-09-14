package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.ContentDetails;
import src.Models.Notes;
import src.Repositories.NotesRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NotesRepository notesRepository;

    @Autowired
    public NoteService(
            NotesRepository notesRepository,
            ContentDetailsService contentDetailsService
    ) {
        this.notesRepository = notesRepository;
    }

    public Notes addNote(ContentDetails contentDetails) {
        var note = new Notes();
        note.setContentDetails(contentDetails);
        note.setStatusOfCompletion(false);

        return notesRepository.save(note);
    }

    public Optional<Notes> updateNote(Notes newNote) {
        return notesRepository.findById(newNote.getId()).map(note -> {
            note.setContentDetails(newNote.getContentDetails());
            note.setStatusOfCompletion(newNote.getStatusOfCompletion());

            return note;
        });
    }

    public Optional<Notes> getNote(UUID id) {
        return notesRepository.findById(id);
    }

    public void deleteNote(UUID id) {
        var note = notesRepository.findById(id).orElseThrow();
        notesRepository.delete(note);
    }
}
