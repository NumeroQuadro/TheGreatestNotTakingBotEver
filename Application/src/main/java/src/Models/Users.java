package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;
    @Column(name = "user_telegram_tag", unique = true, nullable = false)
    private String telegramTag;
    @Column(name = "user_telegram_id", unique = true, nullable = false)
    private Long telegramId;
}