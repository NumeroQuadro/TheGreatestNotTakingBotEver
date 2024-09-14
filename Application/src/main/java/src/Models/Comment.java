package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(nullable = false)
    private String textMessage;
    @Column(nullable = false)
    private Long timecode;
    @Column(name = "page")
    private Integer page;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "note_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "content_details_id"))
    private ContentDetails contentDetails;
}
