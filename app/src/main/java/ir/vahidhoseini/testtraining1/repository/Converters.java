package ir.vahidhoseini.testtraining1.repository;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static String fromArrayString(String[] highlights) {
        String data = "";
        for (String strings : highlights) {
            data = data + "\n" + strings;
        }
        return data;
    }

    @TypeConverter
    public static String[] toString(String[] highlights) {
        String data[] = new String[highlights.length];
        for (int i = 0; i < highlights.length; i++) {
            data[i] = highlights[i];
        }
        return data;
    }


}