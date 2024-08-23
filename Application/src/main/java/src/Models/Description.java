package src.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "description")
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "description_id")
    private Integer id;
    @Column(name = "comment_id")
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    public Description() {
        this.comments = new HashSet<>();
    }

    public Description addNewComment(Comment comment) {
        comments.add(comment);

        return this;
    }
}