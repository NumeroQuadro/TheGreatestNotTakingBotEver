package src;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import src.GrpcImplementation.NotesServiceImpl;
import src.Models.ContentDetails;
import src.Models.ContentTypeEnum;
import src.Services.ContentDetailsService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddNoteSpecificDetails_AddedSuccessfully_ReturnNoteSpecificDetails {
    @Mock
    private ContentDetailsService contentDetailsService;
    @InjectMocks
    private NotesServiceImpl notesServiceImpl;

    private src.UUID getConvertedUUID(java.util.UUID uuid) {
        return src.UUID.newBuilder().setValue(uuid.toString()).build();
    }
    private java.util.UUID getConvertedUUID(src.UUID uuid) {
        return java.util.UUID.fromString(uuid.toString());
    }

    @Test
    public void addNoteSpecificDetailsWithNormalizedUrlAddedSuccessfullyReturnNoteSpecificDetailsWithNormalizedurl() {
        var normalizedUrl = "https://www.youtube.com/watch?v=ooAmMKrYRHI";

        var request = AddContentDetailsRequest.newBuilder()
                .setContentShortName("test")
                .setContentType(ContentType.VIDEO)
                .setContentUrl(normalizedUrl)
                .build();
        var expectedContentDetails = new ContentDetails();
        var uuid = UUID.randomUUID();
        expectedContentDetails.setId(uuid);
        expectedContentDetails.setContentUrl(normalizedUrl);
        expectedContentDetails.setContentType(ContentTypeEnum.VIDEO);
        expectedContentDetails.setContentShortName("test");

        when(contentDetailsService.addContentDetails(
                anyString(),
                anyString(),
                any(ContentTypeEnum.class))
        ).thenReturn(expectedContentDetails);

        StreamRecorder<AddContentDetailsResponse> responseObserver = StreamRecorder.create();

        notesServiceImpl.addContentDetails(request, responseObserver);

        Assertions.assertNotNull(responseObserver.getValues());
        Assertions.assertEquals(1, responseObserver.getValues().size());
        AddContentDetailsResponse response = responseObserver.getValues().get(0);
        Assertions.assertEquals(getConvertedUUID(expectedContentDetails.getId()), response.getContentDetails().getId());
        Assertions.assertEquals(normalizedUrl, response.getContentDetails().getContentUrl());
        Assertions.assertEquals(expectedContentDetails.getContentShortName(), response.getContentDetails().getContentShortName());
        Assertions.assertEquals(ContentType.VIDEO, response.getContentDetails().getContentType());

        verify(contentDetailsService).addContentDetails(anyString(), anyString(), any(ContentTypeEnum.class));
    }
}
