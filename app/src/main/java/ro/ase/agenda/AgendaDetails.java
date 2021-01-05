package ro.ase.agenda;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "details", foreignKeys = @ForeignKey(entity = Profile.class, parentColumns = "id", childColumns = "idProfile", onDelete = CASCADE), indices = @Index("idProfile"))
public class AgendaDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date lastMessage;
    private String message;
    private int idProfile;

    public AgendaDetails(Date lastMessage, String message, int idProfile) {
        this.lastMessage = lastMessage;
        this.message = message;
        this.idProfile = idProfile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Date lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    @Override
    public String toString() {
        return "AgendaDetails{" +
                "id=" + id +
                ", lastMessage=" + lastMessage +
                ", message='" + message + '\'' +
                '}';
    }
}
