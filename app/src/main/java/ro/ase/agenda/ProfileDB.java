package ro.ase.agenda;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Profile.class, AgendaDetails.class}, version = 2, exportSchema = false)
@TypeConverters({DateConvertor.class, EnumConvertor.class})

public abstract class ProfileDB extends RoomDatabase {

    private final static String DB_NAME="profile.db";
    private static ProfileDB instanta;

    public static ProfileDB getInstanta(Context context)
    {
        if(instanta==null)
            instanta = Room.databaseBuilder(context, ProfileDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return instanta;
    }

    public abstract ProfileDAO getProfileDao();

    public abstract AgendaDetailsDAO getDetailsDao();
}
