package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import retrofit2.Call;
import retrofit2.Response;

public class MainApiClient {
    private static final String TAG = "ZomatoApiClient";
    private static MainApiClient instance;
    private MutableLiveData<List<Restaurants>> mListLiveResturants;
    private RetrieveResturantsRunnable mRetrieveResturantsRunnable;
    private int mWhichMutableLiveData;
    private Map<Integer, List<Restaurants>> mListResturantsAndIds = new HashMap<>();

    public static MainApiClient getInstance() {
        if (instance == null) {
            instance = new MainApiClient();
        }
        return instance;
    }

    public MainApiClient() {
        mListLiveResturants = new MutableLiveData<>();
    }


    public LiveData<List<Restaurants>> getMainListResturants() {
        return mListLiveResturants;
    }

    private Thread thread;

    public void ReciveResturantsApi(Map<String, Object> params , int page) {
        mWhichMutableLiveData = Integer.parseInt((String) params.get("category"));
        if (mListResturantsAndIds.get(mWhichMutableLiveData) == null) {
            Log.e(TAG, "onChanged: list in map is null : ");

            List<Restaurants> restaurants = new ArrayList<>();
            mListResturantsAndIds.put(mWhichMutableLiveData, restaurants);
        }

        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable = null;
        }
        mRetrieveResturantsRunnable = new RetrieveResturantsRunnable(params , page);

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

        Map<String, Object> params;
        boolean cancelRequest;
        int page;


        public RetrieveResturantsRunnable( Map<String, Object> p , int page) {
            this.cancelRequest = false;
            this.page = page;
            params = p;
            params.put("start", String.valueOf(page));

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

                    if (page == 1) {
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
