package ir.vahidhoseini.testtraining1.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

@Database(entities = {Collection.class, Restaurants.class, Category.class}, version =6)
public abstract class DataBase extends RoomDatabase {

    private static DataBase instance;
    private static final String DATABASE_NAME = "COLLECTION_DB";

    public static DataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }


    public abstract ZomatoDao getZomatoDao();
}
