package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.vahidhoseini.testtraining1.AppExecutors;
import ir.vahidhoseini.testtraining1.model.zomato.Categories;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurant;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.CategoryResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.CollectionResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import retrofit2.Call;
import retrofit2.Response;

import static ir.vahidhoseini.testtraining1.utill.Constant.NETWORK_TIMEOUT;

public class ZomatoApiClient {
    private static final String TAG = "ZomatoApiClient";
    private static ZomatoApiClient instance;
    private MutableLiveData<List<Categories>> mCategory;
    private MutableLiveData<List<Collections>> mCollection;
    private MutableLiveData<List<Restaurants>> mResturants;
    private RetrieveCategoryRunnable mRetrieveCategoryRunnable;
    private RetrieveCollectionRunnable mRetrieveCollectionRunnable;
    private RetrieveResturantsRunnable mRetrieveResturantsRunnable;

    public static ZomatoApiClient getInstance() {
        if (instance == null) {
            instance = new ZomatoApiClient();
        }
        return instance;
    }

    public ZomatoApiClient() {
        mCategory = new MutableLiveData<>();
        mCollection = new MutableLiveData<>();
        mResturants = new MutableLiveData<>();

        List<MutableLiveData<List<Restaurants>>> listOfLiveData;


    }

    public LiveData<List<Categories>> getCategory() {
        return mCategory;
    }

    public void ReciveCategoryApi() {
        if (mRetrieveCategoryRunnable != null) {
            mRetrieveCategoryRunnable = null;
        }
        mRetrieveCategoryRunnable = new RetrieveCategoryRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveCategoryRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public LiveData<List<Collections>> getCollection() {
        return mCollection;
    }

    private Thread thread;

    public void ReciveCollectionApi(int cityId, int count) {
        if (mRetrieveCollectionRunnable != null) {
            mRetrieveCollectionRunnable = null;
        }
        mRetrieveCollectionRunnable = new RetrieveCollectionRunnable(cityId, count);
        if (thread != null) {
            thread = null;
        }
        thread = new Thread(mRetrieveCollectionRunnable);
    }

    public LiveData<List<Restaurants>> getResturants() {
        return mResturants;
    }


    public void ReciveResturantsApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable = null;
        }
        mRetrieveResturantsRunnable = new RetrieveResturantsRunnable(query, start, count, lat, lon, cuisines, category, sort, order);
        if (thread != null) {
            thread = null;
        }
        thread = new Thread(mRetrieveResturantsRunnable);
        thread.start();
    }


    private class RetrieveCategoryRunnable implements Runnable {

        boolean cancelRequest;

        public RetrieveCategoryRunnable() {
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getCategoryResponse().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Categories> list = ((CategoryResponse) response.body()).getCategories();
                    mCategory.postValue(list);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "error :" + error);
                    mCategory.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mCategory.postValue(null);
            }

        }

        private Call<CategoryResponse> getCategoryResponse() {
            return ServiceGenerator.getApi().getCategory();
        }

        private void CancelRequest() {
            Log.d(TAG, "CancelRequest: canceling the getCategory request");
            cancelRequest = true;
        }
    }

    private class RetrieveCollectionRunnable implements Runnable {

        boolean cancelRequest;
        int cityId;
        int count;

        public RetrieveCollectionRunnable(int cityId, int count) {
            this.cityId = cityId;
            this.count = count;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getCollectionResponse().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Collections> list = ((CollectionResponse) response.body()).getCollectionResponse();
                    mCollection.postValue(list);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "response have an error :" + error);
                    mCollection.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "error :" + e.getMessage());
                mCollection.postValue(null);
            }

        }

        private Call<CollectionResponse> getCollectionResponse() {
            return ServiceGenerator.getApi().getCollection(cityId, count);
        }

        private void CancelRequest() {
            //            Log.d(TAG, "CancelRequest: canceling the getCollectionResponse request");
            cancelRequest = true;
        }
    }

    private class RetrieveResturantsRunnable implements Runnable {

        boolean cancelRequest;
        String query;
        int start;
        int count;
        double lat;
        double lon;
        String cuisines;
        String category;
        String sort;
        String order;

        public RetrieveResturantsRunnable(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
            this.cancelRequest = false;
            this.query = query;
            this.start = start;
            this.count = count;
            this.lat = lat;
            this.lon = lon;
            this.cuisines = cuisines;
            this.category = category;
            this.sort = sort;
            this.order = order;
        }


        @Override
        public void run() {
            try {
                Response response = getResturantResponse().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Restaurants> list = null;
                    list = ((ResturantResponse) response.body()).getResturantResponse();
                    if(list.size()>1) {
                        if (start == 1) {
                            mResturants.postValue(list);
                        } else {
                            List<Restaurants> currentResturants = mResturants.getValue();
                            currentResturants.addAll(list);
                            mResturants.postValue(currentResturants);
                        }
                    }else {
                        mResturants.postValue(null);
                    }
                } else {
                    String error = response.errorBody().string();
                    //                    Log.e(TAG, "ZomatoApiClient error :" + error);
                    mResturants.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //                Log.e(TAG, "ZomatoApiClient error IOException:" + e.getMessage());
                mResturants.postValue(null);
            }

        }

        private Call<ResturantResponse> getResturantResponse() {
            return ServiceGenerator.getApi().getResturant(query, start, count, lat, lon, cuisines, "", sort, order);
        }


        private void CancelRequest() {
            Log.d(TAG, "CancelRequest: canceling the getCollectionResponse request");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveCategoryRunnable != null) {
            mRetrieveCategoryRunnable.CancelRequest();
        } else if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable.CancelRequest();
        } else if (mRetrieveCollectionRunnable != null) {
            mRetrieveCollectionRunnable.CancelRequest();
        }
    }

}
