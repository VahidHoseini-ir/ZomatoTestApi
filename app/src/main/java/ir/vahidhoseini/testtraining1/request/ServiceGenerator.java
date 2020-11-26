package ir.vahidhoseini.testtraining1.request;

import ir.vahidhoseini.testtraining1.utill.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Constant.ZOMATO_URL).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitBuilder.build();
    private static Api api = retrofit.create(Api.class);


    public static Api getApi() {
        return api;
    }
}
