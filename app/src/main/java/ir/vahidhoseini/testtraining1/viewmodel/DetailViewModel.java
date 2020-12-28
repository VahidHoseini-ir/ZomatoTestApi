package ir.vahidhoseini.testtraining1.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.Reviews;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.repository.Repository;

public class DetailViewModel extends AndroidViewModel {

    private Repository mRepository;
    private boolean mPerformingQuery = false;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        mRepository = Repository.getInstance();
    }

    public void reciveReviews(Map<String, Object> mapReviews) {
        mRepository.reciveReviews(mapReviews);
    }

    public LiveData<List<Reviews>> getReviews() {
        return mRepository.getReviews();
    }


    public LiveData<List<Restaurants>> getResturants() {
        return mRepository.getResturants();
    }



    public void reciveResturantApi(Map<String, Object> p) {
        if (!mPerformingQuery) {
            mPerformingQuery = true;
            mRepository.reciveResturantApi(p);
        }
    }
    public void performingQueryFinished() {
        mPerformingQuery = false;
    }


}
