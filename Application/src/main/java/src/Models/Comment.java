package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;
    @Column(name = "text_message")
    private String textMessage;
    @Column(name = "timecode")
    private Long timecode;
    @Column(name = "page")
    private Integer page;
}
