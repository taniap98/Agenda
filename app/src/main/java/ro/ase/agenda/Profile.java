package ro.ase.agenda;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

enum Category {
    FAMILY,
    CLOSEFRIENDS,
    WORK,
    SCHOOL
}

@Entity(tableName = "profiles")
public class Profile {

    @Ignore
    private String uid;

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Category category;
    private String date;

    @Ignore
    public Profile(){

    }

    public Profile(String firstName, String lastName, String phone, String email, Category category, String date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.category = category;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName + " " +lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Category getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", category=" + category +
                ", date=" + date +
                '}';
    }
}
