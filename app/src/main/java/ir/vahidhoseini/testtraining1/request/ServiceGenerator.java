package ir.vahidhoseini.testtraining1.request;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.CallbackHandler;

import ir.vahidhoseini.testtraining1.utill.Constant;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String HEADER_CACHE_CONTROL = "Cache-control";
    public static final String HEADER_PRAGMA = "Pragma";
    private static final long cacheSize =10*1024*1024;
    private static Cache cache() {
        return new Cache(new File(MyApplication.instance.getCacheDir(),"ZomatoCach"),cacheSize);
    }
    private static OkHttpClient okHttpClient =
            new OkHttpClient.Builder()
                    .cache(cache())
                    .addInterceptor(httpLoggingInterceptor())//used if network of or on
                    .addNetworkInterceptor(networkInterceptor())// only used when nerwork is on
                    .addInterceptor(offlineInterceptor())
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Log.d("TAG", "offline interceptor: called.");
                Request request = chain.request();

                //prevent caching when network is on. For that we use the "networkInterceptor"
                if(!MyApplication.hasNetwork()){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(30 , TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();

                }
                return chain.proceed(request);
            }
        };
    }
    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Log.d("TAG" , "network interceptor:called");

                Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1,TimeUnit.DAYS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static Interceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
                {
                   @Override
                   public void log(String message){
                       Log.d("TAG" , "log : http log :"+message);
                   }
                });
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }


    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constant.ZOMATO_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static Api api = retrofit.create(Api.class);


    public static Api getApi() {
        return api;
    }
}
