package ir.vahidhoseini.testtraining1.repository;

import android.util.Log;

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
    private String query;
    private int start;
    private int count;
    private double lat;
    private double lon;
    private String cuisines;
    private String category;
    private String sort;
    private String order;


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

    public void reciveResturantApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        this.query = query != null ? query : this.query != null ? this.query : "";
        this.start = start;
        this.count = count;
        this.lat = lat;
        this.lon = lon;
        this.cuisines = cuisines;
        this.category = category;
        this.sort = sort;
        this.order = order;
        ApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void nextReciveResturantApi() {
        start = (start == 1 ? 0 : start) + 10;
        Log.e("TAG", "ZomatoApiClient in next recive start is :" + start);
        ApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void reciveColletionApi(Map<String, Object> params) {
        ApiClient.ReciveCollectionApi(params);
    }

    public void reciveMainResturantsApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        this.query = query;
        this.start = start;
        this.count = count;
        this.lat = lat;
        this.lon = lon;
        this.cuisines = cuisines;
        this.category = category;
        this.sort = sort;
        this.order = order;
        mApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void nextReciveMainResturantApi(String query, int start, int count, String category) {
        mApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void cancelRequest() {
        ApiClient.cancelRequest();
    }
}
