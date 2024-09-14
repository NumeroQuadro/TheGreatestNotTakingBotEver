package src;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import src.GrpcImplementation.NotesServiceImpl;
import src.Models.Comment;
import src.Services.CommentService;

import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddComment_AddedSuccessfully_ReturnComment {
    @Mock
    private CommentService commentService;

    @InjectMocks
    private NotesServiceImpl notesServiceImpl;

    private src.UUID getConvertedUUID(java.util.UUID uuid) {
        return src.UUID.newBuilder().setValue(uuid.toString()).build();
    }
    private java.util.UUID getConvertedUUID(src.UUID uuid) {
        return java.util.UUID.fromString(uuid.toString());
    }

    @Test
    public void addCommentWithTimecodeAddedSuccessfullyReturnComment() {
        AddCommentRequest request = AddCommentRequest.newBuilder()
                .setPage(0)
                .setTextMessage("sample")
                .setTimecode(2938L)
                .build();
        src.Models.Comment expectedComment = new Comment();
        var uuid = UUID.randomUUID();
        expectedComment.setId(uuid);
        expectedComment.setPage(0);
        expectedComment.setTimecode(2938L);
        expectedComment.setTextMessage("sample");

        when(commentService.addComment(anyString(), anyLong(), anyInt())).thenReturn(expectedComment);

        StreamRecorder<AddCommentResponse> responseObserver = StreamRecorder.create();

        notesServiceImpl.addComment(request, responseObserver);

        Assertions.assertNotNull(responseObserver.getValues());
        Assertions.assertEquals(1, responseObserver.getValues().size());
        AddCommentResponse response = responseObserver.getValues().get(0);
        Assertions.assertEquals(getConvertedUUID(expectedComment.getId()), response.getComment().getId());
        Assertions.assertEquals(0, response.getComment().getPage());
        Assertions.assertEquals(expectedComment.getTimecode(), response.getComment().getTimecode());

        verify(commentService).addComment(anyString(), anyLong(), anyInt());
    }
}
