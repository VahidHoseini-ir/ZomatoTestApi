package ir.vahidhoseini.testtraining1.request;

import java.util.Map;

import ir.vahidhoseini.testtraining1.request.zomatoresponse.CategoryResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.CollectionResponse;
import ir.vahidhoseini.testtraining1.request.zomatoresponse.ResturantResponse;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {
    // Zomato sample header is : "Accept: application/json" --header "user-key: fc44acfa5a5f36d37869fe177107285c" "https://developers.zomato.com/api/v2.1/categories"
    @Headers({"Accept: application/json", "user-key: fc44acfa5a5f36d37869fe177107285c",})
    @GET("api/v2.1/categories")
    Call<CategoryResponse> getCategory();

    @Headers({"Accept: application/json", "user-key: fc44acfa5a5f36d37869fe177107285c",})
    @GET("api/v2.1/collections")
    Call<CollectionResponse> getCollection(@Query("city_id") int cityId, @Query("count") int count);

    @Headers({"Accept: application/json", "user-key: fc44acfa5a5f36d37869fe177107285c",})
    @GET("api/v2.1/search")
    Call<ResturantResponse> getResturant(@Query("q") String query,
                                         @Query("start") int start,
                                         @Query("count") int count,
                                         @Query("lat") double lat,
                                         @Query("lon") double lon,
                                         @Query("cuisines") String cuisines,
                                         @Query("category") String category,
                                         @Query("sort") String sort,
                                         @Query("order") String order);

    @Headers({"Accept: application/json", "user-key: fc44acfa5a5f36d37869fe177107285c",})
    @GET("api/v2.1/search")
    Call<ResturantResponse> getTestResturant(@QueryMap Map<String, Object> params);





}
