package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.ContentTypeEnum;
import src.Models.Description;
import src.Models.NoteSpecificDetails;
import src.Repositories.NoteSpecificDetailsRepository;

@Service
public class NoteSpecificDetailsService {
    private NoteSpecificDetailsRepository noteSpecificDetailsRepository;
    private DescriptionService descriptionService;

    @Autowired
    public NoteSpecificDetailsService(NoteSpecificDetailsRepository noteSpecificDetailsRepository, DescriptionService descriptionService) {
        this.noteSpecificDetailsRepository = noteSpecificDetailsRepository;
        this.descriptionService = descriptionService;
    }

    public NoteSpecificDetails addNoteSpecificDetails(String url, String contentShortName, ContentTypeEnum contentType) {
        var noteSpecificDetails = new NoteSpecificDetails();
        noteSpecificDetails.setContentType(contentType);
        noteSpecificDetails.setContentShortName(contentShortName);
        noteSpecificDetails.setContentUrl(url);
        noteSpecificDetails.setDescription(descriptionService.addDescription());
        noteSpecificDetails.setDescription(new Description());

        return noteSpecificDetailsRepository.save(noteSpecificDetails);
    }

    public NoteSpecificDetails addNoteSpecificDetails(String url, String contentShortName, ContentTypeEnum contentType, Description description) {
        var noteSpecificDetails = new NoteSpecificDetails();
        noteSpecificDetails.setContentType(contentType);
        noteSpecificDetails.setContentShortName(contentShortName);
        noteSpecificDetails.setContentUrl(url);
        noteSpecificDetails.setDescription(description);

        return noteSpecificDetailsRepository.save(noteSpecificDetails);
    }

    public NoteSpecificDetails addNoteSpecificDetails(String contentShortName, ContentTypeEnum contentType, Description description) {
        var noteSpecificDetails = new NoteSpecificDetails();
        noteSpecificDetails.setContentType(contentType);
        noteSpecificDetails.setContentShortName(contentShortName);
        noteSpecificDetails.setDescription(description);

        return noteSpecificDetailsRepository.save(noteSpecificDetails);
    }
}
