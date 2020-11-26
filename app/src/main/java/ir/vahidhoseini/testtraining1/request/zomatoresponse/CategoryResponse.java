package ir.vahidhoseini.testtraining1.request.zomatoresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import ir.vahidhoseini.testtraining1.model.zomato.Categories;

public class CategoryResponse {

    @SerializedName("categories")
    @Expose
    private List<Categories> categories;

    public List<Categories> getCategories() {
        return categories;
    }

}
