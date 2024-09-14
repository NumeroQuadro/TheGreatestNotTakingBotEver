package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "content_details_id", nullable = false)
    private ContentDetails contentDetails;
    @Column(nullable = false)
    private Boolean statusOfCompletion;
}