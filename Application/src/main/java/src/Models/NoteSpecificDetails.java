package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notes_specific_details")
public class NoteSpecificDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_details_id")
    private Integer id;
    @Column(name = "description_id", nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "description_id", referencedColumnName = "description_id")
    private Description description;
    @Column(name = "content_short_name", unique = true)
    private String contentShortName;
    @Column(name = "content_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;
}