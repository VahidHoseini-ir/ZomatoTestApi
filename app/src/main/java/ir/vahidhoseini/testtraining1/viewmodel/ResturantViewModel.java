package ir.vahidhoseini.testtraining1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.ZomatoRepository;

public class ResturantViewModel extends ViewModel {

    private ZomatoRepository mZomatoRepository;
    private boolean repositoryIsPerformingQuery;


    public ResturantViewModel() {
        mZomatoRepository = ZomatoRepository.getInstance();
        repositoryIsPerformingQuery = false;
    }



    public void reciveResturantApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        if (!repositoryIsPerformingQuery) {
            repositoryIsPerformingQuery = true;
            mZomatoRepository.reciveResturantApi(query, start, count, lat, lon, cuisines, category, sort, order);
        }
    }

    public LiveData<List<Restaurants>> getResturants() {
        return mZomatoRepository.getResturants();
    }

    public void nextReciveResturantApi() {
        if (!repositoryIsPerformingQuery) {
            repositoryIsPerformingQuery = true;
            mZomatoRepository.nextReciveResturantApi();
        }
    }

    public void repeatLastNextReciveResturant() {
        if (!repositoryIsPerformingQuery) {
            repositoryIsPerformingQuery = true;
            mZomatoRepository.repeatLastNextReciveResturant();
        }
    }

    public void performingQuery() {
        repositoryIsPerformingQuery = false;
    }


  }
