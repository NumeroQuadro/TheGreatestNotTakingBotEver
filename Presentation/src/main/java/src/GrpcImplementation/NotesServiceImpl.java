package src.GrpcImplementation;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import src.*;
import src.Models.ContentTypeEnum;
import src.Models.NoteComments;
import src.Repositories.UsersRepository;
import src.Services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotesServiceImpl extends NotesServiceGrpc.NotesServiceImplBase {
    private final UserService userService;
    private final NoteService noteService;
    private final NoteCommentsService noteCommentsService;
    private final ContentDetailsService contentDetailsService;
    private final CommentService commentService;
    private final UserNotesLinksService userNotesLinksService;
    private final Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);
    private final ConversionService conversionService;
    private final UsersRepository usersRepository;

    @Autowired
    public NotesServiceImpl(
            UserService userService,
            NoteService noteService,
            NoteCommentsService noteCommentsService,
            ContentDetailsService contentDetailsService,
            CommentService commentService,
            UserNotesLinksService userNotesLinksService,
            ConversionService conversionService, UsersRepository usersRepository) {
        this.userService = userService;
        this.noteService = noteService;
        this.noteCommentsService = noteCommentsService;
        this.contentDetailsService = contentDetailsService;
        this.commentService = commentService;
        this.userNotesLinksService = userNotesLinksService;
        this.conversionService = conversionService;
        this.usersRepository = usersRepository;
    }

    @Override
    public void addUser(
            AddUserRequest request,
            StreamObserver<AddUserResponse> responseObserver
    ) {
        var user = userService.addUser(request.getUserTelegramId());
        var response = AddUserResponse.newBuilder()
                .setUser(Users.newBuilder()
                        .setId(UUID.newBuilder()
                                .setValue(user.getId()
                                        .toString()))
                        .setUserTelegramId(user.getTelegramId())
                        .build()
                )
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private ContentType getConvertedEnumType(ContentTypeEnum contentTypeEnum) {
        return switch (contentTypeEnum) {
            case ARTICLE -> ContentType.ARTICLE;
            case BOOK -> ContentType.BOOK;
            case PODCAST -> ContentType.PODCAST;
            default -> ContentType.VIDEO;
        };
    }

    private ContentTypeEnum getConvertedEnumType(ContentType contentType) {
        return switch (contentType) {
            case ARTICLE -> ContentTypeEnum.ARTICLE;
            case BOOK -> ContentTypeEnum.BOOK;
            case PODCAST -> ContentTypeEnum.PODCAST;
            default -> ContentTypeEnum.VIDEO;
        };
    }

    private java.util.UUID getConvertedUUID(UUID uuid) {
        return java.util.UUID.fromString(uuid.getValue());
    }

    private UUID getConvertedUUID(java.util.UUID uuid) {
        return UUID.newBuilder().setValue(uuid.toString()).build();
    }


    @Override
    public void linkNoteWithUser(
            LinkNoteWithUserRequest request,
            StreamObserver<LinkNoteWithUserResponse> responseObserver
    ) {
        var usersNotesLinks = userNotesLinksService.addUserWithNote(
                getConvertedUUID(request.getUserId()),
                getConvertedUUID(request.getNoteId())
        );

        var response = LinkNoteWithUserResponse.newBuilder()
                .setUserNotesLinks(UserNotesLinks.newBuilder()
                        .setUserId(UUID.newBuilder()
                                .setValue(usersNotesLinks.getUser().getId().toString())
                                .build())
                        .setNoteId(UUID.newBuilder()
                                .setValue(usersNotesLinks.getNote().getId().toString())
                                .build())
                        .setId(UUID.newBuilder()
                                .setValue(usersNotesLinks.getId().toString())
                                .build())
                        .build()
                )
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addContentDetails(
            AddContentDetailsRequest request,
            StreamObserver<AddContentDetailsResponse> responseObserver
    ) {
        var contentDetails = contentDetailsService.addContentDetails(
                request.getContentUrl(),
                request.getContentShortName(),
                getConvertedEnumType(request.getContentType())
        );

        var response = AddContentDetailsResponse.newBuilder()
                .setContentDetails(ContentDetails.newBuilder()
                        .setId(UUID.newBuilder()
                                .setValue(contentDetails.getId().toString())
                                .build())
                        .setContentTypeValue(contentDetails.getContentType().ordinal())
                        .setContentUrl(contentDetails.getContentUrl())
                        .setContentShortName(contentDetails.getContentShortName())
                        .build()
                )
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addNote(
            AddNoteRequest request,
            StreamObserver<AddNoteResponse> responseObserver
    ) {
        try {
            var contentDetails = contentDetailsService.getContentDetails(getConvertedUUID(request.getContentDetailsId())).orElseThrow();
            var note = noteService.addNote(contentDetails);

            var response = AddNoteResponse.newBuilder()
                    .setNote(Notes.newBuilder()
                            .setId(getConvertedUUID(note.getId()))
                            .setContentDetailsId(getConvertedUUID(note.getContentDetails().getId()))
                            .setStatusOfCompletion(note.getStatusOfCompletion())
                            .build()
                    )
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void addNoteComments(
            AddNoteCommentsRequest request,
            StreamObserver<AddNoteCommentsResponse> responseObserver
    ) {
        var noteComments = noteCommentsService.addNoteComments(
                getConvertedUUID(request.getCommentId()),
                getConvertedUUID(request.getContentDetailsId())
        );

        var response = AddNoteCommentsResponse.newBuilder()
                .setNoteComments(
                        src.NoteComments.newBuilder()
                                .setCommentId(getConvertedUUID(noteComments.getId()))
                                .setContentDetailsId(getConvertedUUID(noteComments.getContentDetails().getId()))
                                .build()
                )
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getNoteCommentsById(
            src.GetNoteCommentsByIdRequest request,
            io.grpc.stub.StreamObserver<src.GetNoteCommentsByIdResponse> responseObserver) {
        try {
            var noteComments = noteCommentsService.getNoteCommentById(getConvertedUUID(
                    request.getNoteCommentsId())
            ).orElseThrow();

            var response = GetNoteCommentsByIdResponse.newBuilder()
                    .setNoteComments(src.NoteComments.newBuilder()
                            .setCommentId(getConvertedUUID(noteComments.getId()))
                            .setContentDetailsId(getConvertedUUID(noteComments.getContentDetails().getId()))
                            .build())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Internal server error: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void getNoteCommentsByCommentId(
            src.GetNoteCommentsByCommentIdRequest request,
            io.grpc.stub.StreamObserver<src.GetNoteCommentsByCommentIdResponse> responseObserver
    ) {
        var arrayNoteComments = noteCommentsService.getNoteCommentsByCommentId(
                getConvertedUUID(request.getCommentId())
        );

        List<src.NoteComments> convertedNoteCommentsList = arrayNoteComments.stream()
                .map(noteComments -> src.NoteComments.newBuilder()
                        .setCommentId(getConvertedUUID(noteComments.getComment().getId()))
                        .setId(getConvertedUUID(noteComments.getId()))
                        .setContentDetailsId(getConvertedUUID(noteComments.getContentDetails().getId()))
                        .build())
                .toList();

        var response = GetNoteCommentsByCommentIdResponse.newBuilder()
                .addAllNoteComments(convertedNoteCommentsList)
                .build();
    }


    @Override
    public void getCommentsByContentDetailsId(
            src.GetCommentsByContentDetailsIdRequest request,
            io.grpc.stub.StreamObserver<src.GetCommentsByContentDetailsIdResponse> responseObserver
    ) {
        try {
            var arrayNoteComments = noteCommentsService.getNoteCommentsByContentDetailsId(
                    getConvertedUUID(request.getContentDetailsId())
            );

            List<src.Models.Comment> arrayComments = arrayNoteComments.stream()
                    .map(noteComments -> commentService.getComment(
                            noteComments.getComment().getId()).orElseThrow()
                    )
                    .toList();
            List<src.Comment> arrayConvertedComments = arrayComments.stream()
                    .map(comment -> Comment.newBuilder()
                            .setTextMessage(comment.getTextMessage())
                            .setId(getConvertedUUID(comment.getId()))
                            .setPage(comment.getPage())
                            .setTimecode(comment.getTimecode())
                            .build())
                    .toList();

            var response = GetCommentsByContentDetailsIdResponse.newBuilder()
                    .addAllComments(arrayConvertedComments)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Internal server error: " + e.getMessage())
                    .asRuntimeException());
        }
    }

    @Override
    public void addComment(
            AddCommentRequest request,
            StreamObserver<AddCommentResponse> responseObserver
    ) {
        try {
            var comment = commentService.addComment(
                    request.getTextMessage(),
                    request.getTimecode(),
                    request.getPage()
            );

            var response = AddCommentResponse.newBuilder()
                    .setComment(Comment.newBuilder()
                            .setId(getConvertedUUID(comment.getId()))
                            .setPage(comment.getPage())
                            .setTimecode(comment.getTimecode())
                            .setTimecode(comment.getTimecode()))
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
                    .setUser(Users.newBuilder()
                            .setUserTelegramId(user.getTelegramId())
                            .setId(getConvertedUUID(user.getId()))
                            .build())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void getUserById(src.GetUserByIdRequest request,
                            io.grpc.stub.StreamObserver<src.GetUserResponse> responseObserver) {
        try {
            var user = userService.getUser(getConvertedUUID(request.getUserId())).orElseThrow();

            var response = GetUserResponse.newBuilder()
                    .setUser(Users.newBuilder()
                            .setId(getConvertedUUID(user.getId()))
                            .setUserTelegramId(user.getTelegramId())
                            .build()
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
    public void getNoteById(src.GetNoteByIdRequest request,
                            io.grpc.stub.StreamObserver<src.GetNoteResponse> responseObserver) {
        try {
            var note = noteService.getNote(getConvertedUUID(request.getNoteId())).orElseThrow();

            var response = GetNoteResponse.newBuilder()
                    .setNote(Notes.newBuilder()
                            .setContentDetailsId(getConvertedUUID(note.getContentDetails().getId()))
                            .setId(getConvertedUUID(note.getId()))
                            .setStatusOfCompletion(note.getStatusOfCompletion())
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
    public void getContentDetailsByShortName(
            src.GetContentDetailsByShortNameRequest request,
            StreamObserver<GetArrayContentDetailsResponse> responseObserver
    ) {
        try {
            var contentDetails = contentDetailsService.getAllNoteSpecificDetails();
            List<src.ContentDetails> filteredComments = contentDetails.stream()
                    .filter(comment -> comment
                            .getContentShortName()
                            .equals(request.getContentShortName()))
                    .map(detail -> ContentDetails.newBuilder()
                            .setId(getConvertedUUID(detail.getId()))
                            .setContentShortName(detail.getContentShortName())
                            .setContentUrl(detail.getContentUrl())
                            .setContentType(getConvertedEnumType(detail.getContentType()))
                            .build())
                    .collect(Collectors.toList());

            var response = GetArrayContentDetailsResponse.newBuilder()
                    .addAllArrayContentDetails(filteredComments)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void getContentDetailsByUrl(
            src.GetContentDetailsByUrlRequest request,
            StreamObserver<GetArrayContentDetailsResponse> responseObserver
    ) {
        try {
            var contentDetails = contentDetailsService.getAllNoteSpecificDetails();
            List<src.ContentDetails> filteredComments = contentDetails.stream()
                    .filter(comment -> comment
                            .getContentShortName()
                            .equals(request.getContentUrl()))
                    .map(detail -> ContentDetails.newBuilder()
                            .setId(getConvertedUUID(detail.getId()))
                            .setContentShortName(detail.getContentShortName())
                            .setContentUrl(detail.getContentUrl())
                            .setContentType(getConvertedEnumType(detail.getContentType()))
                            .build())
                    .collect(Collectors.toList());

            var response = GetArrayContentDetailsResponse.newBuilder()
                    .addAllArrayContentDetails(filteredComments)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void getContentDetailsById(
            GetContentDetailsByIdRequest request,
            StreamObserver<GetContentDetailsByIdResponse> responseObserver
    ) {
        try {
            var contentDetails = contentDetailsService.getContentDetails(
                    getConvertedUUID(request.getContentDetailsId())
            ).orElseThrow();

            var response = GetContentDetailsByIdResponse.newBuilder()
                    .setContentDetails(ContentDetails.newBuilder()
                            .setContentShortName(contentDetails.getContentShortName())
                            .setContentType(getConvertedEnumType(contentDetails.getContentType()))
                            .setContentUrl((contentDetails.getContentUrl()))
                            .setId(getConvertedUUID(contentDetails.getId()))
                            .build()
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
    public void getComment(
            src.GetCommentRequest request,
            io.grpc.stub.StreamObserver<src.GetCommentResponse> responseObserver
    ) {
        try {
            var comment = commentService.getComment(
                    getConvertedUUID(request.getCommentId())
            ).orElseThrow();

            var response = GetCommentResponse.newBuilder()
                    .setComment(Comment.newBuilder()
                            .setId(getConvertedUUID(comment.getId()))
                            .setPage(comment.getPage())
                            .setTextMessage(comment.getTextMessage())
                            .setTimecode(comment.getTimecode())
                            .build()
                    ).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void getUsersNotes(
            src.GetUsersNotesRequest request,
            io.grpc.stub.StreamObserver<src.GetUsersNotesResponse> responseObserver
    ) {
        try {
            var usersNotesArray = userNotesLinksService.getNotesRelatedWithUserById(
                    getConvertedUUID(request.getUserId())
            );
            var notes = usersNotesArray.stream()
                    .map(usersNotes -> noteService.getNote(usersNotes.getNote().getId()).orElseThrow())
                    .toList();
            var convertedNotes = notes.stream()
                    .map(note -> Notes.newBuilder()
                            .setId(getConvertedUUID(note.getId()))
                            .setContentDetailsId(getConvertedUUID(note.getContentDetails().getId()))
                            .setStatusOfCompletion(note.getStatusOfCompletion())
                            .build())
                    .toList();

            var response = GetUsersNotesResponse.newBuilder()
                    .addAllNotes(convertedNotes).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void removeUser(
            src.RemoveUserRequest request,
            io.grpc.stub.StreamObserver<src.RemoveUserResponse> responseObserver
    ) {
        try {
            userService.deleteUser(getConvertedUUID(request.getUserId()));

            var response = RemoveUserResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void removeNote(src.RemoveNoteRequest request,
                           io.grpc.stub.StreamObserver<src.RemoveNoteResponse> responseObserver) {
        try {
            noteService.deleteNote(getConvertedUUID(request.getNoteId()));

            var response = RemoveNoteResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error(e.toString());
            responseObserver.onError(e);
        }
    }
}
