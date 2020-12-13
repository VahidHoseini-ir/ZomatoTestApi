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

public class ResturantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants = new ArrayList<>();
    private OnClickListener onClickListener;
    public final int ResturantView = 1;
    public final int LoadingView = 2;
    public final int ExhaustedView = 3;


    public ResturantAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case ResturantView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_resturant, viewGroup, false);
                return new ResturantViewHolder(view, onClickListener, 0);
            }
            case LoadingView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_progress, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case ExhaustedView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_viewexhausted, viewGroup, false);
                return new LoadingViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == ResturantView) {
            if (!resturants.get(position).getRestaurant().getFeatured_image().isEmpty()) {
                Log.w("tokhmi", "getFeatured_image: " + resturants.get(position).getRestaurant().getFeatured_image());
                Picasso.get().load(resturants.get(position).getRestaurant().getFeatured_image()).placeholder(R.drawable.returant_placeholder).error(R.drawable.returant_placeholder).into(((ResturantViewHolder) holder).featured_image);
            } else {
                Picasso.get().load(R.drawable.returant_placeholder).into(((ResturantViewHolder) holder).featured_image);
            }
            ((ResturantViewHolder) holder).timing.setText(resturants.get(position).getRestaurant().getTimings());
            ((ResturantViewHolder) holder).cuisines.setText(resturants.get(position).getRestaurant().getCuisines());
            ((ResturantViewHolder) holder).name.setText(resturants.get(position).getRestaurant().getName());
            ((ResturantViewHolder) holder).average_cost.setText("Average cost for two : " + resturants.get(position).getRestaurant().getAverage_cost_for_two() + " " + resturants.get(position).getRestaurant().getCurrency());
            ((ResturantViewHolder) holder).city.setText("City : " + resturants.get(position).getRestaurant().getLocation().getCity());
            ((ResturantViewHolder) holder).locality.setText("Locality : " + resturants.get(position).getRestaurant().getLocation().getLocality());
            ((ResturantViewHolder) holder).address.setText("Address : " + resturants.get(position).getRestaurant().getLocation().getAddress());
            ((ResturantViewHolder) holder).phone_number.setText("Phone number : " + resturants.get(position).getRestaurant().getPhone_numbers());
        }


    }


    @Override
    public int getItemViewType(int position) {

        if (resturants.get(position).getId() == LoadingView) {
            return LoadingView;
        } else if (resturants.get(position).getId() == ExhaustedView) {
            return ExhaustedView;
        } else if (position == resturants.size() - 1 && position != 0 && resturants.get(position).getId() != ExhaustedView) {
            return LoadingView;
        } else {
            return ResturantView;
        }
    }

    @Override
    public int getItemCount() {
        if (resturants != null) {
            return resturants.size();
        }
        return 0;
    }

    public void displayLoading() {
        if (!isLoading()) {
            Restaurants restaurants = new Restaurants();
            restaurants.setId(LoadingView);
            resturants.add(restaurants);
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (resturants != null) {
            if (resturants.size() > 0) {
                if (resturants.get(resturants.size() - 1).getId() == LoadingView) {
                    return true;
                }
            }
        }
        return false;
    }

    private void hideLoading() {
        for (int i = 0; i < resturants.size(); i++) {
            Restaurants restaurant = resturants.get(i);
            if (restaurant.getId() == LoadingView) {
                resturants.remove(restaurant);
            }
        }
        notifyDataSetChanged();

    }

    public void setRecyclerExauhsted() {
        hideLoading();
        Restaurants restaurants = new Restaurants();
        restaurants.setId(ExhaustedView);
        resturants.add(restaurants);
        notifyDataSetChanged();
    }

    public void setResturants(List<Restaurants> Resturants) {
        hideLoading();
        this.resturants = Resturants;
        notifyDataSetChanged();
    }


}
