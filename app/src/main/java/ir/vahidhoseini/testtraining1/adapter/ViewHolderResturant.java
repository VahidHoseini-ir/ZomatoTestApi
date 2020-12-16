package ir.vahidhoseini.testtraining1.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ViewHolderResturant extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView featured_image;
    TextView timing;
    TextView cuisines;
    TextView name;
    TextView average_cost;
    TextView city;
    TextView locality;
    TextView address;
    TextView phone_number;
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
        city = itemView.findViewById(R.id.city);
        locality = itemView.findViewById(R.id.locality);
        address = itemView.findViewById(R.id.address);
        phone_number = itemView.findViewById(R.id.phone_number);
        itemView.setOnClickListener(this);
    }

    public void setResturant(Restaurants resturant) {
        if (!resturant.getRestaurant().getFeatured_image().isEmpty()) {
            Picasso.get().load(resturant.getRestaurant().getFeatured_image()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(featured_image);
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(featured_image);
        }
        timing.setText(resturant.getRestaurant().getTimings());
        cuisines.setText(resturant.getRestaurant().getCuisines());
        name.setText(resturant.getRestaurant().getName());
        average_cost.setText("Average cost for two : " + resturant.getRestaurant().getAverage_cost_for_two() + " " + resturant.getRestaurant().getCurrency());
        city.setText("City : " + resturant.getRestaurant().getLocation().getCity());
        locality.setText("Locality : " + resturant.getRestaurant().getLocation().getLocality());
        address.setText("Address : " + resturant.getRestaurant().getLocation().getAddress());
        phone_number.setText("Phone number : " + resturant.getRestaurant().getPhone_numbers());


    }

    @Override
    public void onClick(View view) {
        onClickListener.onClickListener(getAdapterPosition());
    }
}



