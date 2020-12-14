package ir.vahidhoseini.testtraining1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.Repository;

public class CategoriesViewModel extends AndroidViewModel {
    private Repository mZomatoRepository;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        mZomatoRepository = Repository.getInstance();
    }

    public void reciveMainResturantsApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        mZomatoRepository.reciveMainResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public LiveData<List<Restaurants>> getMainListResturants() {
        return mZomatoRepository.getMainListResturants();
    }

    public LiveData<List<Collections>> getCollections() {
        return mZomatoRepository.getCollections();
    }


}
