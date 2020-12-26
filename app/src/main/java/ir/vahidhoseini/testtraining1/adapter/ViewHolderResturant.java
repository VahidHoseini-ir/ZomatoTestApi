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

    ImageView featured_image;
    TextView timing;
    TextView cuisines;
    TextView name;
    TextView average_cost;
    TextView rate;
    TextView address;
    OnClickListener onClickListener;
    int categoryId;

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

    Restaurants mResturant;

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



