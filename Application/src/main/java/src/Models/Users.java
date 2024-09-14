package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(name = "user_telegram_id", unique = true, nullable = false)
    private Long telegramId;
}