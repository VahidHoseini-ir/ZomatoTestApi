package ir.vahidhoseini.testtraining1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class AdapterSimilarResturant extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants = new ArrayList<>();
    private OnClickListener onClickListener;
    public final int ResturantView = 1;
    public final int LoadingView = 2;


    public AdapterSimilarResturant(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case ResturantView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_similar_resturant, viewGroup, false);
                return new ViewHolderResturant(view, onClickListener);
            }
            case LoadingView: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.loading_progress, viewGroup, false);
                return new ViewHolderLoading(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == ResturantView) {
            ((ViewHolderResturant) holder).setSimilarResturant(resturants.get(position));
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (resturants.get(position).getId() == LoadingView) {
            return LoadingView;
        } else if (position == resturants.size() - 1 && resturants.get(position).getId() == LoadingView) {
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
            List<Restaurants> list = new ArrayList<>();
            Restaurants restaurants = new Restaurants();
            restaurants.setId(LoadingView);
            list.add(restaurants);
            resturants = list;
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

    //    private void hideLoading() {
    //        List<Restaurants> toRemove = new ArrayList<>();
    //        for (Restaurants restaurant:resturants) {
    //            if (restaurant.getId() == LoadingView) {
    //                toRemove.add(restaurant);
    //            }
    //        }
    //        resturants.removeAll(toRemove);
    //        notifyDataSetChanged();
    //
    //    }
    private void hideLoading() {
        if(resturants.size()>0){
            if (resturants.get(resturants.size() - 1).getId() == LoadingView) {
                resturants.remove(resturants.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    public void setResturants(List<Restaurants> Resturants) {
        hideLoading();
        this.resturants = Resturants;
        notifyDataSetChanged();
    }


}
