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

public class AdapterResturant extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants = new ArrayList<>();
    private OnClickListener onClickListener;
    public final int ResturantView = 1;
    public final int LoadingView = 2;
    public final int ExhaustedView = 3;


    public AdapterResturant(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case ResturantView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_resturant, viewGroup, false);
                return new ViewHolderResturant(view, onClickListener, 0);
            }
            case LoadingView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_progress, viewGroup, false);
                return new ViewHolderLoading(view);
            }
            case ExhaustedView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_viewexhausted, viewGroup, false);
                return new ViewHolderLoading(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == ResturantView) {
            ((ViewHolderResturant) holder).setResturant(resturants.get(position));
        } else if (itemViewType == ExhaustedView) {
            ((ViewHolderLoading) holder).textview.setText(resturants.size() > 1 ? "No more items found" : "What are you looking for?, no items found.");
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

    public void displayLoading(boolean newQuery) {// if the user looking for new query i remove all list and then show progress again
        if (newQuery) {
            if (!isLoading()) {
                List<Restaurants> list = new ArrayList<>();
                Restaurants restaurants = new Restaurants();
                restaurants.setId(LoadingView);
                list.add(restaurants);
                resturants = list;
                notifyDataSetChanged();
            }
        } else {
            if (!isLoading()) {
                Restaurants restaurants = new Restaurants();
                restaurants.setId(LoadingView);
                resturants.add(restaurants);
                notifyDataSetChanged();
            }
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

    public void setRecyclerExauhsted(boolean noItems) {
        if (noItems) {
            hideLoading();
            List<Restaurants> list = new ArrayList<>();
            Restaurants restaurants = new Restaurants();
            restaurants.setId(ExhaustedView);
            list.add(restaurants);
            resturants = list;
            notifyDataSetChanged();
        } else {
            hideLoading();
            Restaurants restaurants = new Restaurants();
            restaurants.setId(ExhaustedView);
            resturants.add(restaurants);
            notifyDataSetChanged();
        }
    }

    public void setResturants(List<Restaurants> Resturants) {
        hideLoading();
        this.resturants = Resturants;
        notifyDataSetChanged();
    }


}
