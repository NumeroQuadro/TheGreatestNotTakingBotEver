package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.Comment;
import src.Models.Description;
import src.Repositories.DescriptionRepository;

import java.util.Set;

@Service
public class DescriptionService {
    private DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionService(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public Description addDescription() {
        var description = new Description();

        return descriptionRepository.save(description);
    }

    public Description addDescription(Comment comment) {
        var description = new Description();
        description.addNewComment(comment);

        return descriptionRepository.save(description);
    }

    public Description addDescription(Set<Comment> comments) {
        var description = new Description();
        description.setComments(comments);

        return descriptionRepository.save(description);
    }

    public Description getDescription(Integer descriptionId) {
        return descriptionRepository.findById(descriptionId).orElseThrow();
    }

    public void deleteDescription(Integer descriptionId) {
        var description = descriptionRepository.findById(descriptionId);
        description.ifPresent(descrip -> descriptionRepository.delete(descrip));
    }
}
