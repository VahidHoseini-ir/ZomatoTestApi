package ir.vahidhoseini.testtraining1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.Repository;

public class ResturantViewModel extends ViewModel {

    private Repository mZomatoRepository;
    private boolean mPerformingQuery = false;


    public ResturantViewModel() {
        mZomatoRepository = Repository.getInstance();
    }


    public void reciveResturantApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        if (!mPerformingQuery) {
            mPerformingQuery = true;
            mZomatoRepository.reciveResturantApi(query, start, count, lat, lon, cuisines, category, sort, order);
        }
    }

    public LiveData<List<Restaurants>> getResturants() {
        return mZomatoRepository.getResturants();
    }

    public void nextReciveResturantApi() {
        if (!mPerformingQuery) {
            mPerformingQuery = true;
            mZomatoRepository.nextReciveResturantApi();
        }
    }

    public void performingQueryFinished() {
        mPerformingQuery = false;
    }


}
