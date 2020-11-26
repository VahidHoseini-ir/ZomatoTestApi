package ir.vahidhoseini.testtraining1.request.zomatoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.vahidhoseini.testtraining1.model.zomato.Collections;

public class CollectionResponse {

    @SerializedName("collections")
    @Expose
    private List<Collections> CollectionResponse;

    public List<Collections> getCollectionResponse() {
        return CollectionResponse;
    }
}
