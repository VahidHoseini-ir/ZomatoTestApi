package ir.vahidhoseini.testtraining1.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.view.DetailResturantActivity;

public class ViewHolderResturant extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Restaurants mResturant;

    ImageView featured_image;
    TextView timing;
    TextView cuisines;
    TextView name;
    TextView average_cost;
    TextView rate;
    TextView address;
    OnClickListener onClickListener;
    int categoryId;

    /**
     * in this constractor i just set view for main resturant view in main activity or in resturant activity
     *
     * @param itemView
     * @param onClickListener
     * @param categoryId
     */
    public ViewHolderResturant(@NonNull View itemView, OnClickListener onClickListener, int categoryId) {
        super(itemView);
        this.onClickListener = onClickListener;
        this.categoryId = categoryId;
        featured_image = itemView.findViewById(R.id.featured_image);
        timing = itemView.findViewById(R.id.timing);
        cuisines = itemView.findViewById(R.id.cuisines);
        name = itemView.findViewById(R.id.name);
        average_cost = itemView.findViewById(R.id.average_cost);
        rate = itemView.findViewById(R.id.rate);
        address = itemView.findViewById(R.id.address);
        itemView.setOnClickListener(this);
    }

    /**
     * i use this method in restorant activity and main activity becuase all view are set and
     * all data in those activity should set so i use this method in adapter resturant
     *
     * @param resturant
     */
    public void setResturant(Restaurants resturant) {
        mResturant = resturant;

        timing.setText(resturant.getRestaurant().getTimings().replace(" ", ""));
        cuisines.setText(resturant.getRestaurant().getCuisines().replace(" ", ""));
        name.setText(resturant.getRestaurant().getName());
        average_cost.setText(resturant.getRestaurant().getCurrency() + resturant.getRestaurant().getAverage_cost_for_two() + "for two");
        address.setText(resturant.getRestaurant().getLocation().getAddress() + "," + resturant.getRestaurant().getLocation().getCity());
        if (!resturant.getRestaurant().getFeatured_image().isEmpty()) {
            Picasso.get().load(resturant.getRestaurant().getFeatured_image()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(featured_image);
            featured_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            featured_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Picasso.get().load(R.drawable.returant_placeholder).into(featured_image);
        }
        rate.setText(resturant.getRestaurant().getUser_rating().getAggregate_rating());

    }


    /**
     * in this constractor i just change the layout of view holder and use it in
     * AdapterSimilarResturant
     *
     * @param itemView
     * @param onClickListener
     */
    public ViewHolderResturant(@NonNull View itemView, OnClickListener onClickListener) {
        super(itemView);
        this.onClickListener = onClickListener;
        featured_image = itemView.findViewById(R.id.featured_image);
        name = itemView.findViewById(R.id.name);
        rate = itemView.findViewById(R.id.rate);
        address = itemView.findViewById(R.id.address);
        itemView.setOnClickListener(this);
    }

    /**
     * i just use this method in adapter similar resturant to show the similar resturant in detail activity
     * becuase in that activity no need to show all datas and views so i can use this method
     *
     * @param resturant
     */
    public void setSimilarResturant(Restaurants resturant) {
        mResturant = resturant;
        name.setText(resturant.getRestaurant().getName());
        address.setText(resturant.getRestaurant().getLocation().getLocality());
        if (!resturant.getRestaurant().getThumb().isEmpty()) {
            Picasso.get().load(resturant.getRestaurant().getThumb()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(featured_image);
            featured_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            featured_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Picasso.get().load(R.drawable.returant_placeholder).into(featured_image);
        }
        rate.setText(resturant.getRestaurant().getUser_rating().getAggregate_rating());

    }


    /*
    this onClick work on both of views wich are first in main activity and resturant actiivty that is for search
    and for edtail activity becuase in both adapter a pass the resturant and its engough to make this onclick work.
     */
    @Override
    public void onClick(View view) {
        try {
            //            Long id = new JSONObject(mResturant.getRestaurant().getR().toString()).getLong("res_id");

            Intent intent = new Intent(MyApplication.currentActivity, DetailResturantActivity.class);
            intent.putExtra("Restaurants", mResturant.getRestaurant());
            intent.putExtra("Location", mResturant.getRestaurant().getLocation());
            intent.putExtra("Rate", mResturant.getRestaurant().getUser_rating());
            MyApplication.currentActivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



