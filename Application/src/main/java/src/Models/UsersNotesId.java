package src.Models;

import java.io.Serializable;

public class UsersNotesId implements Serializable {
    private Integer userId;
    private Integer noteId;

    public UsersNotesId(Integer userId, Integer noteId) {
        this.userId = userId;
        this.noteId = noteId;
    }
}
