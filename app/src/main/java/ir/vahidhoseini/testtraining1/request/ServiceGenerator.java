package ir.vahidhoseini.testtraining1.request;

import java.util.concurrent.TimeUnit;

import ir.vahidhoseini.testtraining1.utill.Constant;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build();
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Constant.ZOMATO_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static Api api = retrofit.create(Api.class);


    public static Api getApi() {
        return api;
    }
}
