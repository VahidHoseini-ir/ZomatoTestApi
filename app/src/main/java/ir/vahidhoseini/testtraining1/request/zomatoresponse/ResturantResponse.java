package ir.vahidhoseini.testtraining1.request.zomatoresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ResturantResponse {

    @SerializedName("restaurants")
    private List<Restaurants> ResturantResponse;

    @SerializedName("results_found")
    private String result_found;

    @SerializedName("results_start")
    private String results_start;

    public List<Restaurants> getResult_found() {
        return ResturantResponse;
    }

    public List<Restaurants> getResturantResponse() {
        return ResturantResponse;
    }
}
