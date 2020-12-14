package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.CollectionResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import retrofit2.Call;
import retrofit2.Response;

public class ApiClient {
    private static final String TAG = "ZomatoApiClient";
    private static ApiClient instance;
    private MutableLiveData<List<Collections>> mCollection;
    private MutableLiveData<List<Restaurants>> mResturants;
    private RetrieveCollectionRunnable mRetrieveCollectionRunnable;
    private RetrieveResturantsRunnable mRetrieveResturantsRunnable;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiClient() {
        mCollection = new MutableLiveData<>();
        mResturants = new MutableLiveData<>();
    }


    public LiveData<List<Collections>> getCollection() {
        return mCollection;
    }

    private Thread thread;

    public void ReciveCollectionApi(Map<String, Object> params) {
        if (mRetrieveCollectionRunnable != null) {
            mRetrieveCollectionRunnable = null;
        }
        mRetrieveCollectionRunnable = new RetrieveCollectionRunnable(params);
        if (thread != null) {
            thread = null;
        }
        thread = new Thread(mRetrieveCollectionRunnable);
        thread.start();
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

    private class RetrieveCollectionRunnable implements Runnable {

        boolean cancelRequest;
        Map<String, Object> params;

        public RetrieveCollectionRunnable(Map<String, Object> p) {
            params = p;
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
                    Log.e(TAG, "ZomatoApiClient error :" + list.toString());

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
            return ServiceGenerator.getApi().getCollection(params);
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
                    if (list.size() > 1) {
                        if (start == 1) {
                            mResturants.postValue(list);
                        } else {
                            List<Restaurants> currentResturants = mResturants.getValue();
                            currentResturants.addAll(list);
                            mResturants.postValue(currentResturants);
                        }
                    } else {
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
        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable.CancelRequest();
        } else if (mRetrieveCollectionRunnable != null) {
            mRetrieveCollectionRunnable.CancelRequest();
        }
    }

}
