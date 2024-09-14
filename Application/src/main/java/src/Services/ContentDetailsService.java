package src.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.Models.ContentDetails;
import src.Models.ContentTypeEnum;
import src.Repositories.ContentDetailsRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContentDetailsService {
    private final ContentDetailsRepository contentDetailsRepository;

    @Autowired
    public ContentDetailsService(
            ContentDetailsRepository contentDetailsRepository
    ) {
        this.contentDetailsRepository = contentDetailsRepository;
    }

    public ContentDetails addContentDetails(
            String url,
            String contentShortName,
            ContentTypeEnum contentType
    ) {
        var contentDetails = new ContentDetails();
        contentDetails.setContentType(contentType);
        contentDetails.setContentShortName(contentShortName.isEmpty() ? null : contentShortName);
        contentDetails.setContentUrl(url.isEmpty() ? null : normalizeYouTubeUrl(url));

        return contentDetailsRepository.save(contentDetails);
    }

    public Optional<ContentDetails> getContentDetails(UUID id) {
        return contentDetailsRepository.findById(id);
    }

    public Optional<ContentDetails> updateContentDetails(UUID uuid, ContentDetails newContentDetails) {
        return contentDetailsRepository.findById(uuid).map(contentDetails -> {
            contentDetails.setContentType(newContentDetails.getContentType());
            contentDetails.setContentShortName(newContentDetails.getContentShortName());
            contentDetails.setContentUrl(newContentDetails.getContentUrl());
            contentDetails.setComments(newContentDetails.getComments());

            return contentDetails;
        });
    }

    public List<ContentDetails> getAllNoteSpecificDetails() {
        return contentDetailsRepository.findAll();
    }

    public Map<UUID, ContentDetails> getNotesSpecificDetailsByIds(List<UUID> ids) {
        List<ContentDetails> detailsList = contentDetailsRepository.findAllByIdIn(ids);

        return detailsList.stream().collect(Collectors.toMap(ContentDetails::getId, details -> details));
    }

    private String normalizeYouTubeUrl(String url) {
        String youtubeWatchRegex = "(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([\\w-]+)";
        String liveStreamRegex = "(?:https?://)?(?:www\\.)?(?:youtube\\.com/live/)([\\w-]+)";

        Pattern watchPattern = Pattern.compile(youtubeWatchRegex);
        Matcher watchMatcher = watchPattern.matcher(url);
        if (watchMatcher.find()) {
            String videoId = watchMatcher.group(1);
            return "https://www.youtube.com/watch?v=" + videoId;
        }

        Pattern livePattern = Pattern.compile(liveStreamRegex);
        Matcher liveMatcher = livePattern.matcher(url);
        if (liveMatcher.find()) {
            String videoId = liveMatcher.group(1);
            return "https://www.youtube.com/watch?v=" + videoId;
        }

        return url;
    }
}
