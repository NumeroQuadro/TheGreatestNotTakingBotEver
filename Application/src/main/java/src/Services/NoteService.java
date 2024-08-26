package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.NoteSpecificDetails;
import src.Models.Notes;
import src.Repositories.NotesRepository;

@Service
public class NoteService {
    private NotesRepository notesRepository;
    private NoteSpecificDetailsService noteSpecificDetailsService;

    @Autowired
    public NoteService(NotesRepository notesRepository, NoteSpecificDetailsService noteSpecificDetailsService) {
        this.notesRepository = notesRepository;
        this.noteSpecificDetailsService = noteSpecificDetailsService;
    }

    public Notes addNote(NoteSpecificDetails noteSpecificDetails) {
        var note = new Notes();
        note.setNoteSpecificDetails(noteSpecificDetails);
        note.setStatusOfCompletion(false);

        return notesRepository.save(note);
    }

    public Notes addNote(NoteSpecificDetails noteSpecificDetails, Boolean statusOfCompletion) {
        var note = new Notes();
        note.setNoteSpecificDetails(noteSpecificDetails);
        note.setStatusOfCompletion(statusOfCompletion);

        return notesRepository.save(note);
    }
}
