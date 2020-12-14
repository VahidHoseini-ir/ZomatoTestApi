package ir.vahidhoseini.testtraining1.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;
import ir.vahidhoseini.testtraining1.utill.MyApplication;
import ir.vahidhoseini.testtraining1.view.DetailResturantActivity;

public class AdapterMainResturant extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnClickListener {

    private List<Restaurants> resturants;
    private int categoryId;
    private OnClickListener onClickListener;


    public AdapterMainResturant(int categoryId) {
        this.categoryId = categoryId;
        onClickListener = this;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_main_item_resturant, viewGroup, false);
        return new ViewHolderResturant(view, onClickListener, categoryId);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (!resturants.get(position).getRestaurant().getFeatured_image().isEmpty()) {
            Picasso.get().load(resturants.get(position).getRestaurant().getThumb()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(((ViewHolderResturant) holder).featured_image);
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(((ViewHolderResturant) holder).featured_image);
        }
        ((ViewHolderResturant) holder).timing.setText(resturants.get(position).getRestaurant().getTimings());
        ((ViewHolderResturant) holder).cuisines.setText(resturants.get(position).getRestaurant().getCuisines());
        ((ViewHolderResturant) holder).name.setText(resturants.get(position).getRestaurant().getName());
        ((ViewHolderResturant) holder).average_cost.setText("for 2 : " + resturants.get(position).getRestaurant().getAverage_cost_for_two() + " " + resturants.get(position).getRestaurant().getCurrency());


    }

    @Override
    public int getItemCount() {
        if (resturants != null) {
            return resturants.size();
        }
        return 0;
    }

    public void setResturants(List<Restaurants> resturants) {
        this.resturants = resturants;
        notifyDataSetChanged();
    }

    @Override
    public void onClickListener(int position) {
//        Toast.makeText(MyApplication.currentActivity, "POSITION :" + resturants.get(position).getRestaurant(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyApplication.currentActivity, DetailResturantActivity.class);
        intent.putExtra("Restaurants", resturants.get(position).getRestaurant());
        intent.putExtra("Location", resturants.get(position).getRestaurant().getLocation());
        MyApplication.currentActivity.startActivity(intent);
    }

    //                mViewModel.getResturant(position, category-1);
    //                Intent intent = new Intent(MainActivity.this, DetailResturantActivity.class);
    //                intent.putExtra("Restaurants", mViewModel.getResturant(position, category));
    //                startActivity(intent);
    //                Toast.makeText(MainActivity.this, "POSITION :" + position, Toast.LENGTH_SHORT).show();
}
