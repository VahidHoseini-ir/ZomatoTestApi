package ir.vahidhoseini.testtraining1.repository;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.Map;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.ApiClient;
import ir.vahidhoseini.testtraining1.request.MainApiClient;

public class Repository {
    private ApiClient ApiClient;
    private MainApiClient mApiClient;
    private static Repository instance;
    private Map<String, Object> params;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public Repository() {
        ApiClient = ApiClient.getInstance();
        mApiClient = MainApiClient.getInstance();
    }

    public LiveData<List<Collections>> getCollections() {
        return ApiClient.getCollection();
    }

    public LiveData<List<Restaurants>> getResturants() {
        return ApiClient.getResturants();
    }

    public LiveData<List<Restaurants>> getMainListResturants() {
        return mApiClient.getMainListResturants();
    }

    public void reciveResturantApi(Map<String, Object> p) {
        params = p;
        ApiClient.ReciveResturantsApi(params, 1);
    }

    public void nextReciveResturantApi() {
        int page = (Integer.parseInt((String) params.get("start")) == 1 ? 0 : Integer.parseInt((String) params.get("start"))) + 10;// if start== 1 it returns 0 else returns start+10
        ApiClient.ReciveResturantsApi(params, page);
    }

    public void reciveColletionApi(Map<String, Object> params) {
        ApiClient.ReciveCollectionApi(params);
    }

    public void reciveMainResturantsApi(Map<String, Object> p) {
        params = p;
        mApiClient.ReciveResturantsApi(params, 1);
    }

    public void cancelRequest() {
        ApiClient.cancelRequest();
    }
}
