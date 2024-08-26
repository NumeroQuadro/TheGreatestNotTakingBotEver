package src.GrpcImplementation;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GrpcServer {
    private final NotesServiceImpl notesService;

    @Autowired
    public GrpcServer(NotesServiceImpl notesService) {
        this.notesService = notesService;
    }

    public void run() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(notesService)
                .build();

        server.start();
        System.out.println("Server started, listening on " + 50051);
        server.awaitTermination();
    }
}
