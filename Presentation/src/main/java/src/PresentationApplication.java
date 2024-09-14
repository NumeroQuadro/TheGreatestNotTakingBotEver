package src;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import src.GrpcImplementation.NotesServiceImpl;

@SpringBootApplication
@ComponentScan(basePackages = {"src.Services", "src.Repositories", "org.security.notetakingbot", "src.GrpcImplementation", "src"})
@EntityScan(basePackages = {"src.Models"})
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
