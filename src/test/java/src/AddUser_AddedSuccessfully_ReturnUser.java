package src;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import src.GrpcImplementation.NotesServiceImpl;
import src.Models.Users;
import src.Services.UserService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddUser_AddedSuccessfully_ReturnUser {
    @Mock
    private UserService userService;

    private src.UUID getConvertedUUID(java.util.UUID uuid) {
        return src.UUID.newBuilder().setValue(uuid.toString()).build();
    }
    private java.util.UUID getConvertedUUID(src.UUID uuid) {
        return java.util.UUID.fromString(uuid.toString());
    }

    @InjectMocks
    private NotesServiceImpl notesServiceImpl;
    @Test
    public void addUserAddedSuccessfullyReturnUser() {
        AddUserRequest request = AddUserRequest.newBuilder()
                .setUserTelegramId(12345)
                .build();
        Users expectedUser = new Users();
        var uuid = UUID.randomUUID();
        expectedUser.setId(uuid);
        expectedUser.setTelegramId(12345L);

        when(userService.addUser(anyLong())).thenReturn(expectedUser);

        StreamRecorder<AddUserResponse> responseObserver = StreamRecorder.create();

        // Act
        notesServiceImpl.addUser(request, responseObserver);

        // Assert
        Assertions.assertNotNull(responseObserver.getValues());
        Assertions.assertEquals(1, responseObserver.getValues().size());
        AddUserResponse response = responseObserver.getValues().get(0);
        Assertions.assertEquals(getConvertedUUID(expectedUser.getId()), response.getUser().getId());

        verify(userService).addUser(anyLong());
    }

    @Test
    public void addUserWithNegativeIdAddedSuccessfullyReturneUser() {
        AddUserRequest request = AddUserRequest.newBuilder()
                .setUserTelegramId(-9283827368L)
                .build();
        Users expectedUser = new Users();
        var uuid = UUID.randomUUID();
        expectedUser.setId(uuid);
        expectedUser.setTelegramId(-9283827368L);

        when(userService.addUser(anyLong())).thenReturn(expectedUser);

        StreamRecorder<AddUserResponse> responseObserver = StreamRecorder.create();

        // Act
        notesServiceImpl.addUser(request, responseObserver);

        // Assert
        Assertions.assertNotNull(responseObserver.getValues());
        Assertions.assertEquals(1, responseObserver.getValues().size());
        AddUserResponse response = responseObserver.getValues().get(0);
        Assertions.assertEquals(getConvertedUUID(expectedUser.getId()), response.getUser().getId());

        verify(userService).addUser(anyLong());
    }

}
