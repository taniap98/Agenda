package ro.ase.agenda;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProfileDAO {
    @Insert
    void insert(Profile p);

    @Insert
    void insert(List<Profile> profiles);

    @Query("select * from profiles")
    List<Profile> getAll();

    @Query("SELECT * FROM profiles WHERE phone = :phone")
    Profile findByProfileId(String phone);

    @Query("delete from profiles")
    void deleteAll();

    @Delete
    void deleteOne(Profile p);


}
