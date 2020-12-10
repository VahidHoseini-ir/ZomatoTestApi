package ir.vahidhoseini.testtraining1.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;

public class MainViewModel extends AndroidViewModel {
    private ZomatoRepository mZomatoRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mZomatoRepository = ZomatoRepository.getInstance(application);
    }

    public LiveData<List<Category>> getCategories() {
        return mZomatoRepository.getCategory();
    }


    public void reciveMainResturantsApi( String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
            mZomatoRepository.reciveMainResturantsApi( query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public LiveData<List<Restaurants>> getMainListResturants() {
        return mZomatoRepository.getMainListResturants();
    }


}
