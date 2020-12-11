package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class MainResturantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants;
    private OnClickListener onListItemClickListener;


    public MainResturantAdapter(OnClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_main_item_resturant, viewGroup, false);
        return new ResturantViewHolder(view, onListItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (!resturants.get(position).getRestaurant().getFeatured_image().isEmpty()) {
            Picasso.get().load(resturants.get(position).getRestaurant().getThumb()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(((ResturantViewHolder) holder).featured_image);
        } else {
            Picasso.get().load(R.drawable.returant_placeholder).into(((ResturantViewHolder) holder).featured_image);
        }
        ((ResturantViewHolder) holder).timing.setText(resturants.get(position).getRestaurant().getTimings());
        ((ResturantViewHolder) holder).cuisines.setText(resturants.get(position).getRestaurant().getCuisines());
        ((ResturantViewHolder) holder).name.setText(resturants.get(position).getRestaurant().getName());
        ((ResturantViewHolder) holder).average_cost.setText("for 2 : " + resturants.get(position).getRestaurant().getAverage_cost_for_two() + " " + resturants.get(position).getRestaurant().getCurrency());


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

}
