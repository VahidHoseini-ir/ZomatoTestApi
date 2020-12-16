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
import retrofit2.http.QueryMap;

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


    public void ReciveResturantsApi(Map<String, Object> params, int page) {
        if (mRetrieveResturantsRunnable != null) {
            mRetrieveResturantsRunnable = null;
        }
        mRetrieveResturantsRunnable = new RetrieveResturantsRunnable(params, page);
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
            cancelRequest = true;
        }
    }

    private class RetrieveResturantsRunnable implements Runnable {

        boolean cancelRequest;
        int page;
        Map<String, Object> params;

        public RetrieveResturantsRunnable(Map<String, Object> p, int page) {
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
                    List<Restaurants> list = null;
                    list = ((ResturantResponse) response.body()).getResturantResponse();
                    if (list.size() > 1) {
                        if (page == 1) {
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
            return ServiceGenerator.getApi().getResturant(params);
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
