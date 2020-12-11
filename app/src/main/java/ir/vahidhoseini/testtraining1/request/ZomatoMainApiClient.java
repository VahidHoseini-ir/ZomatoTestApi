package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import ir.vahidhoseini.testtraining1.AppExecutors;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurant;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import retrofit2.Call;
import retrofit2.Response;

import static ir.vahidhoseini.testtraining1.utill.Constant.NETWORK_TIMEOUT;

public class ZomatoMainApiClient {
    private static final String TAG = "ZomatoApiClient";
    private static ZomatoMainApiClient instance;
    private MutableLiveData<List<Restaurants>> mListLiveResturants;
    private MutableLiveData<List<Restaurants>> mNextListLiveResturants;
    private RetrieveResturantsRunnable mRetrieveResturantsRunnable;
    private int mWhichMutableLiveData;
    private Map<Integer, List<Restaurants>> mListResturantsAndIds = new HashMap<>();

    public static ZomatoMainApiClient getInstance() {
        if (instance == null) {
            instance = new ZomatoMainApiClient();
        }
        return instance;
    }

    public ZomatoMainApiClient() {
        mListLiveResturants = new MutableLiveData<>();
        mNextListLiveResturants = new MutableLiveData<>();
    }


    public LiveData<List<Restaurants>> getMainListResturants() {
        return mListLiveResturants;
    }
    public LiveData<List<Restaurants>> getNextMainListResturants() {
        return mNextListLiveResturants;
    }


    private Thread thread;

    public void ReciveResturantsApi(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
        mWhichMutableLiveData = Integer.parseInt(category);
        if (mListResturantsAndIds.get(mWhichMutableLiveData) == null) {
            Log.e(TAG, "onChanged: list in map is null : ");

            List<Restaurants> restaurants = new ArrayList<>();
            mListResturantsAndIds.put(mWhichMutableLiveData, restaurants);
        }

        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable = null;
        }
        mRetrieveResturantsRunnable = new RetrieveResturantsRunnable(query, start, count, lat, lon, cuisines, category, sort, order);

        if (thread != null) {
            thread = null;
        }
        thread = new Thread(mRetrieveResturantsRunnable);
        thread.start();
        //        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveResturantsRunnable);
        //        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
        //            @Override
        //            public void run() {
        //                handler.cancel(true);
        //                mListLiveResturants.postValue(null);
        //
        //            }
        //        }, NETWORK_TIMEOUT, TimeUnit.SECONDS);
    }

    private class RetrieveResturantsRunnable implements Runnable {

        Map<String, Object> params = new HashMap<String, Object>();
        boolean cancelRequest;
        int start;


        public RetrieveResturantsRunnable(String query, int start, int count, double lat, double lon, String cuisines, String category, String sort, String order) {
            this.cancelRequest = false;
            params.put("q", query);
            params.put("start", start);
            params.put("count", count);
            params.put("lat", lat);
            params.put("lon", lon);
            //            params.put("cuisines", cuisines);
            params.put("category", category);
            this.start = start;
            Log.e(TAG, "onChanged: category : " + category);

        }


        @Override
        public void run() {
            try {
                Response response = getResturantResponse().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Restaurants> list = ((ResturantResponse) response.body()).getResult_found();
                    Log.e(TAG, "onChanged: in zomato : " + list.toString());

                    if (start == 1) {
                        mListResturantsAndIds.put(mWhichMutableLiveData, list);
                        mListLiveResturants.postValue(list);

                    } else {
                        List<Restaurants> currentResturants = mListResturantsAndIds.get(mWhichMutableLiveData);
                        currentResturants.addAll(list);
                        Log.e(TAG, "ZomatoApiClient currentResturants :" + currentResturants.size());
                        mListResturantsAndIds.put(mWhichMutableLiveData, currentResturants);
                        mListLiveResturants.postValue(currentResturants);

                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "ZomatoApiClient error :" + error);
                    mListLiveResturants.postValue(null);

                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "ZomatoApiClient error IOException:" + e.getMessage());
                mListLiveResturants.postValue(null);
            }

        }

        private Call<ResturantResponse> getResturantResponse() {
            return ServiceGenerator.getApi().getTestResturant(params);
        }


        private void CancelRequest() {
            Log.d(TAG, "CancelRequest: canceling the getCollectionResponse request");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable.CancelRequest();
        }
    }

}
