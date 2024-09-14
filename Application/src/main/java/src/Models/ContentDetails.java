package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "content_details")
public class ContentDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(unique = true)
    private String contentShortName;
    private String contentUrl;
    @Column(nullable = false, columnDefinition = "content_type")
    @Enumerated(EnumType.STRING)
    private ContentTypeEnum contentType;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
}