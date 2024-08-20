package src.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users_notes")
@IdClass(UsersNotesId.class)
public class UsersNotes {
    @Id
    private Integer userId;
    @Id
    private Integer noteId;
}
