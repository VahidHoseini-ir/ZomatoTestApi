package ir.vahidhoseini.testtraining1.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.Categories;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.ZomatoApiClient;

public class ZomatoRepository {
    private ZomatoApiClient mZomatoApiClient;
    private static ZomatoRepository instance;
    private DataBase dataBase;
    private String query;
    private int start;
    private int count;
    private double lat;
    private double lon;
    private String cuisines;
    private String category;
    private String sort;
    private String order;

    public static ZomatoRepository getInstance() {
        if (instance == null) {
            instance = new ZomatoRepository();
        }
        return instance;
    }

    public ZomatoRepository() {
        mZomatoApiClient = ZomatoApiClient.getInstance();
//        dataBase = DataBase.getInstance();
    }

    public LiveData<List<Categories>> getCategory() {
        return mZomatoApiClient.getCategory();
    }

    public LiveData<List<Collections>> getCollections() {
        return mZomatoApiClient.getCollection();
    }

    public LiveData<List<Restaurants>> getResturants() {
        return mZomatoApiClient.getResturants();
    }


    public void reciveResturantApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        this.query = query;
        this.start = start;
        this.count = count;
        this.lat = lat;
        this.lon = lon;
        this.cuisines = cuisines;
        this.category = category;
        this.sort = sort;
        this.order = order;
        mZomatoApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void nextReciveResturantApi() {
        start = (start == 1 ? 0 : start) + 10;
        Log.e("TAG", "ZomatoApiClient in next recive start is :" + start);
        mZomatoApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void repeatLastNextReciveResturant() {
        start = (start == 10 ? 0 : start - 10);
        mZomatoApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public void reciveCategoryApi() {
        mZomatoApiClient.ReciveCategoryApi();
    }

    public void reciveColletionApi(int cityId, int count) {
        mZomatoApiClient.ReciveCollectionApi(cityId, count);
    }

    public void cancelRequest() {
        mZomatoApiClient.cancelRequest();
    }

    public void inserResturantToDB() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Restaurants> restaurants = mZomatoApiClient.getResturants().getValue();
                for (Restaurants restaurants1 : restaurants) {
                    dataBase.getZomatoDao().inserResturants(restaurants1);
                }
            }
        });
//        thread.start();

    }
}
