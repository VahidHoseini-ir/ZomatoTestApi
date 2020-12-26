package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.Review;
import ir.vahidhoseini.testtraining1.model.zomato.Reviews;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.CollectionResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ReviewResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.QueryMap;

public class ApiClient {
    private static final String TAG = "ZomatoApiClient";
    private static ApiClient instance;
    private MutableLiveData<List<Collections>> mCollection;
    private MutableLiveData<List<Restaurants>> mResturants;
    private MutableLiveData<List<Reviews>> mReviews;
    private RetrieveCollectionRunnable mRetrieveCollectionRunnable;
    private RetrieveResturantsRunnable mRetrieveResturantsRunnable;
    private RetrieveReviewsRunnable mRetrieveReviewsRunnable;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiClient() {
        mCollection = new MutableLiveData<>();
        mResturants = new MutableLiveData<>();
        mReviews = new MutableLiveData<>();
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

    public LiveData<List<Reviews>> getReviews() {
        return mReviews;
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

    public void reciveReviews(Map<String, Object> mapReviews, int page) {
        if (mRetrieveReviewsRunnable != null) {
            mRetrieveReviewsRunnable = null;
        }
        mRetrieveReviewsRunnable = new RetrieveReviewsRunnable(mapReviews, page);
        if (thread != null) {
            thread = null;
        }
        thread = new Thread(mRetrieveReviewsRunnable);
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
                        Log.e(TAG, "RetrieveResturantsRunnable:" + page);
                        if (page == 1) {
                            mResturants.postValue(list);
                        } else {
                            List<Restaurants> currentResturants = mResturants.getValue();
                            currentResturants.addAll(list);
                            Log.e(TAG, "RetrieveResturantsRunnable:" + currentResturants.toString());
                            Log.e(TAG, "RetrieveResturantsRunnable:" + currentResturants.size());
                            mResturants.postValue(currentResturants);
                        }
                    } else {
                        mResturants.postValue(null);
                    }
                } else {
                    String error = response.errorBody().string();
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

    private class RetrieveReviewsRunnable implements Runnable {

        boolean cancelRequest;
        int page;
        Map<String, Object> params;

        public RetrieveReviewsRunnable(Map<String, Object> p, int page) {
            this.cancelRequest = false;
            this.page = page;
            params = p;
            params.put("start", String.valueOf(page));
        }


        @Override
        public void run() {
            try {
                Response response = getReviewResponse().execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Reviews> list = null;
                    list = ((ReviewResponse) response.body()).getReviewResponse();
                    if (list.size() > 1) {
                        Log.e(TAG, "RetrieveResturantsRunnable:" + page);
                        if (page == 0) {
                            mReviews.postValue(list);
                        } else {
                            List<Reviews> currentResturants = mReviews.getValue();
                            currentResturants.addAll(list);
                            Log.e(TAG, "RetrieveResturantsRunnable:" + currentResturants.toString());
                            Log.e(TAG, "RetrieveResturantsRunnable:" + currentResturants.size());
                            mReviews.postValue(currentResturants);
                        }
                    } else {
                        mReviews.postValue(null);
                    }
                } else {
                    String error = response.errorBody().string();
                    mReviews.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mReviews.postValue(null);
            }

        }

        private Call<ReviewResponse> getReviewResponse() {
            return ServiceGenerator.getApi().getReviews(params);
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
