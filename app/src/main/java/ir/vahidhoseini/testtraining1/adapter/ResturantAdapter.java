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
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.Collections;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class ResturantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants;
    private OnClickListener onClickListener;
    private int viewHolderType;
    public final int ResturantView = 1;
    public final int LoadingView = 3;
    public final int recyclerviewExhausted = 4;


    public ResturantAdapter(OnClickListener onCollectionClickListener) {
        this.onClickListener = onCollectionClickListener;
    }

    public void setViewType(int viewType) {
        this.viewHolderType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case ResturantView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_resturant, viewGroup, false);
                return new ResturantViewHolder(view, onClickListener);
            }
            case LoadingView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_progress, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case recyclerviewExhausted: {
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
            Log.e("zomato adapter", "getResults_found:" + Integer.parseInt(resturants.get(1).getResults_found()));
            Log.e("zomato adapter", "resturant size:" + resturants.size());
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


    public int getResturantSize() {
        return resturants.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (viewHolderType == ResturantView) {
            if (resturants.get(position).getViewType() == LoadingView) {
                return LoadingView;
            } else if (resturants.size() - 1 == position && position != 0) {
                if (resturants.size() >= Integer.parseInt(resturants.get(1).getResults_found()) - 10) {
                    return recyclerviewExhausted;
                } else {
                    return LoadingView;
                }
            } else {
                return ResturantView;
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (resturants != null) {
            return resturants.size();
        }
        return 0;
    }

    public void displayLoading() {
        if (viewHolderType == ResturantView) {
            if (!isLoading()) {
                Restaurants restaurant = new Restaurants();
                restaurant.setViewType(LoadingView);
                List<Restaurants> restaurants = new ArrayList<>();
                restaurants.add(restaurant);
                resturants = restaurants;
                notifyDataSetChanged();
            }
        }

    }


    private boolean isLoading() {
        if (viewHolderType == ResturantView) {
            if (resturants != null) {
                if (resturants.size() > 0) {
                    if (resturants.get(resturants.size() - 1).getViewType() == LoadingView) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public void setResturants(List<Restaurants> resturants) {
        if (resturants.get(1) != null) {
            this.resturants = resturants;
            notifyDataSetChanged();
        } else {
            resturants.get(1).setResults_found(String.valueOf(resturants.size()));
        }

    }


}
