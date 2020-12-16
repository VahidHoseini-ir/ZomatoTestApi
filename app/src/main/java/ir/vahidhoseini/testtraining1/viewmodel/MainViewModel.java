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

public class MainViewModel extends AndroidViewModel {
    private Repository mZomatoRepository;
    private boolean isPerformingQuery;


    public MainViewModel(@NonNull Application application) {
        super(application);
        mZomatoRepository = Repository.getInstance();
        isPerformingQuery = false;

    }

    public void reciveResturantApi(Map<String, Object> p) {
        if (!isPerformingQuery) {
            isPerformingQuery = true;
            mZomatoRepository.reciveResturantApi(p);
        }
    }

    public void nextReciveResturantApi() {
        if (!isPerformingQuery) {
            isPerformingQuery = true;
            mZomatoRepository.nextReciveResturantApi();
        }
    }

    public LiveData<List<Restaurants>> getMainListResturants() {
        return mZomatoRepository.getResturants();
    }


    public LiveData<List<Collections>> getCollections() {
        return mZomatoRepository.getCollections();
    }

    public void reciveColletionApi(Map<String, Object> params) {
        mZomatoRepository.reciveColletionApi(params);
    }

    public void queryFinished() {
        isPerformingQuery = false;
    }
    public boolean isPerformingQuery(){
        return isPerformingQuery;
    }

}
