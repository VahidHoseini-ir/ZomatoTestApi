package ir.vahidhoseini.testtraining1.utill;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.Map;

public class Param {
    private static Param instanc;

    public static Param getInstanc() {
        if (instanc == null) {
            instanc = new Param();
        }
        return instanc;
    }

    public Map<String, Object> MapCuratedCollections() {
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", MyCurrentLocation.getInstance().getCity_id());
        params.put("lat", MyCurrentLocation.getInstance().getLat());
        params.put("lon", MyCurrentLocation.getInstance().getLat());
        params.put("count", Constant.COUNT_OF_Currated_Collection_In_MainActivity);
        return params;
    }

    public Map<String, Object> MapResturant() {
        Map<String, Object> params = new HashMap<>();
        params.put("q", "");
        params.put("start", "1");
        params.put("count", Constant.COUNT_OF_RESTURANT_LIST_MAIN);
        params.put("lat", MyCurrentLocation.getInstance().getLat());
        params.put("lon", MyCurrentLocation.getInstance().getLat());
        params.put("cuisines", "");
        params.put("category", "");
        params.put("sort", "");
        params.put("order", "");
        return params;
    }

    public Map<String, Object> MapReviews() {
        Map<String, Object> params = new HashMap<>();
        params.put("res_id", 1);
        params.put("start", "0");
        params.put("count", Constant.COUNT_OF_REVIEWS_IN_DETAILACTIVITY);
        return params;
    }

}
