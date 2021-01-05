package ro.ase.agenda;

import androidx.room.TypeConverter;

public class EnumConvertor {

    @TypeConverter
    public static int fromCategoryToInt(Category value) {
        return value.ordinal();
    }

    @TypeConverter
    public static Category fromIntToCategory(int value) {
        return (Category.values()[value]);
    }
}
