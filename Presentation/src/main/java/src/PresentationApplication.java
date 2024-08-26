package src;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import src.GrpcImplementation.NotesServiceImpl;

@SpringBootApplication
public class PresentationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresentationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(NotesServiceImpl n) {
        return args -> {

        };
    }
}
