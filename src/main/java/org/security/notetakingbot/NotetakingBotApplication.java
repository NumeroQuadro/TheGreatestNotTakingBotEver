package org.security.notetakingbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import src.GrpcImplementation.GrpcServer;
import src.Services.UserService;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = {"src.Repositories"})
@ComponentScan(basePackages = {"src.Services", "src.Repositories", "org.security.notetakingbot", "src.GrpcImplementation"})
@EntityScan(basePackages = {"src.Models"})
public class NotetakingBotApplication {
    public static void main(String[] args)   {
        SpringApplication.run(NotetakingBotApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(GrpcServer grpcServer) {
        return args -> {
            grpcServer.run();
        };
    }
}
