package ro.ase.agenda;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AgendaDetailsDAO {
    @Insert
    void insert(AgendaDetails ad);

    @Query("select * from details")
    List<AgendaDetails> getAll();

    @Query("delete from details")
    void deleteAll();

    @Query("select * from details where idProfile=:idProfile")
    List<AgendaDetails> getDetailsProfile(long idProfile);
}
