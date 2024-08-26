package src.GrpcImplementation;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import src.*;
import src.Services.UserService;

import java.util.NoSuchElementException;

@Service
public class NotesServiceImpl extends NotesServiceGrpc.NotesServiceImplBase {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);

    @Autowired
    public NotesServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addUser(AddUserRequest request,  StreamObserver<AddUserResponse> responseObserver) {
        try {
            var user = userService.addNewUser(request.getUserTelegramId(), request.getUserTelegramTag());

            var response = AddUserResponse.newBuilder()
                    .setUser(User.newBuilder()
                            .setUserId(user.getId())
                            .setUserTelegramId(user.getTelegramId())
                            .setUserTelegramTag(user.getTelegramTag())
                    )
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }
    @Override
    public void getUserByTelegramId(GetUserByTelegramIdRequest request, StreamObserver<GetUserResponse> responseObserver) {
        long telegramId = request.getUserTelegramId();

        try {
            var user = userService.getUser(telegramId);
            var response = GetUserResponse.newBuilder()
                    .setUser(User.newBuilder()
                            .setUserId(user.getId())
                            .setUserTelegramId(user.getTelegramId())
                            .setUserTelegramTag(user.getTelegramTag())
                    )
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (NoSuchElementException e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }
}
