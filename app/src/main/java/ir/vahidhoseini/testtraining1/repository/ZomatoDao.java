package ir.vahidhoseini.testtraining1.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurant;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

@Dao
public interface ZomatoDao {

    @Insert
    long[] insertColleciton(List<Collection> collection);

    @Delete
    int delete(Collection... collection);

    @Update
    int update(Collection... collection);

    @Query("SELECT * FROM COLLECTION ")
    LiveData<List<Collection>> getCollections();

    @Insert
    void inserResturants(Restaurants restaurants);

    @Query("SELECT * FROM resturant")
    LiveData<List<Restaurants>> getResturant();


}
