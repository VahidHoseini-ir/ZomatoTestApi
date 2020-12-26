package ir.vahidhoseini.testtraining1.request.zomatoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.Reviews;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ReviewResponse {

    @SerializedName("user_reviews")
    @Expose
    private List<Reviews> ReviewResponse;


    public List<Reviews> getReviewResponse() {
        return ReviewResponse;
    }
}
