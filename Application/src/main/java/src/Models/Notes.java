package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", nullable = false)
    private Integer id;
    @Column(name = "note_details_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_details_id", referencedColumnName = "note_details_id")
    private NoteSpecificDetails noteSpecificDetails;
    @Column(name = "content_url")
    private String contentUrl;
    @Column(name = "status_of_completion")
    private Boolean statusOfCompletion;
}