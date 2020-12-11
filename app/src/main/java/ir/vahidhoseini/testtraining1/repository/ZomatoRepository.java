package ir.vahidhoseini.testtraining1.repository;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import ir.vahidhoseini.testtraining1.model.zomato.Category;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.ZomatoApiClient;
import ir.vahidhoseini.testtraining1.request.ZomatoMainApiClient;

public class ZomatoRepository {
    private ZomatoApiClient mZomatoApiClient;
    private ZomatoMainApiClient mZomatoMainApiClient;
    private static ZomatoRepository instance;
    private String query;
    private int start;
    private int count;
    private double lat;
    private double lon;
    private String cuisines;
    private String category;
    private String sort;
    private String order;
    private static Thread thread;
    private static DataBase dataBase;
    private static MutableLiveData<List<Category>> mListCategory;


    public static ZomatoRepository getInstance() {
        if (instance == null) {
            instance = new ZomatoRepository();
        }
        return instance;
    }

    public ZomatoRepository() {
        mZomatoApiClient = ZomatoApiClient.getInstance();
        mZomatoMainApiClient = ZomatoMainApiClient.getInstance();
    }



    public LiveData<List<Collections>> getCollections() {
        return mZomatoApiClient.getCollection();
    }

    public LiveData<List<Restaurants>> getResturants() {
        return mZomatoApiClient.getResturants();
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


    // Main Activity request
    public static ZomatoRepository getInstance(Context kontext) {
        if (dataBase == null) {
            dataBase = DataBase.getInstance(kontext);
            mListCategory = new MutableLiveData<>();
        }
        if (instance == null) {
            instance = new ZomatoRepository();
        }
        getLiveCategory();
        return instance;
    }


    public LiveData<List<Category>> getCategory() {
        return mListCategory;
    }

    private static void getLiveCategory() {
        if (thread != null) {
            thread = null;
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mListCategory.postValue(dataBase.getZomatoDao().getCategories());
            }
        });
        thread.start();
    }
    public void reciveMainResturantsApi( String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        this.query = query ;
        this.start = start;
        this.count = count;
        this.lat = lat;
        this.lon = lon;
        this.cuisines = cuisines;
        this.category = category;
        this.sort = sort;
        this.order = order;
        mZomatoMainApiClient.ReciveResturantsApi( query, start, count, lat, lon, cuisines, category, sort, order);
    }
    public LiveData<List<Restaurants>> getMainListResturants() {
        return mZomatoMainApiClient.getMainListResturants();
    }
    public void nextReciveMainResturantApi(String query, int start, int count, String category) {
        mZomatoMainApiClient.ReciveResturantsApi(query, start, count, lat, lon, cuisines, category, sort, order);
    }

    public LiveData<List<Restaurants>> getNextMainListResturants() {
        return mZomatoMainApiClient.getNextMainListResturants();
    }

}
