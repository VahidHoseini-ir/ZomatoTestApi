package ir.vahidhoseini.testtraining1.request.zomatoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ResturantResponse {

    @SerializedName("restaurants")
    @Expose
    private List<Restaurants> ResturantResponse;

    @SerializedName("results_found")
    @Expose
    private String result_found;

    @SerializedName("results_start")
    @Expose
    private String results_start;

    public List<Restaurants> getResult_found() {
        return ResturantResponse;
    }

    public List<Restaurants> getResturantResponse() {
        return ResturantResponse;
    }
}
