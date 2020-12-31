package ir.vahidhoseini.testtraining1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.vahidhoseini.testtraining1.R;
import ir.vahidhoseini.testtraining1.model.zomato.Collection;
import ir.vahidhoseini.testtraining1.model.zomato.searchresturants.Restaurants;

public class AdapterResturant extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Restaurants> resturants = new ArrayList<>();
    private Collection detailCollectionForDetailCollectionActivity;
    private OnClickListener onClickListener;
    public final int ResturantView = 1;
    public final int LoadingView = 2;
    public final int ExhaustedView = 3;
    public final int DetailColectionViewForDetailCollectionActivity = 4;


    public AdapterResturant(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case DetailColectionViewForDetailCollectionActivity: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_collection_detail, viewGroup, false);
                return new ViewHolderBanner(view);
            }
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
        } else if (itemViewType == DetailColectionViewForDetailCollectionActivity) {
            ((TextView) holder.itemView.findViewById(R.id.name)).setText(detailCollectionForDetailCollectionActivity.getTitle());
            ((TextView) holder.itemView.findViewById(R.id.description)).setText(detailCollectionForDetailCollectionActivity.getDescription());
            ((TextView) holder.itemView.findViewById(R.id.count_places)).setText(detailCollectionForDetailCollectionActivity.getRes_count() + " Places");
        }


    }


    @Override
    public int getItemViewType(int position) {

        if (resturants.get(position).getId() == DetailColectionViewForDetailCollectionActivity) {
            return DetailColectionViewForDetailCollectionActivity;
        } else if (resturants.get(position).getId() == LoadingView) {
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
        Log.e("TAG", "setRecyclerExauhsted: resturants.size : " + (resturants.size() - 1));

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

    public void setResturants(List<Restaurants> R) {
        if ((resturants.size() - 1) > 0 && (resturants.size() - 1) < R.size()) {
            hideLoading();
            Log.e("TAG", "if: resturants.size : " + (resturants.size() - 1) + "R.size : " + R.size());
            List<Restaurants> list = R.subList(resturants.size() - 1, R.size());
            this.resturants.addAll(list);
            notifyDataSetChanged();
            Log.e("TAG", "if: resturants.size : " + (resturants.size() - 1));
        } else if((resturants.size() - 1) < R.size()){
            Log.e("TAG", "else: resturants.size : " + (resturants.size() - 1) + "R.size : " + R.size());
            hideLoading();
            Log.e("TAG", "else: after hideAllExceptCollection resturants.size : " + (resturants.size() - 1) + "R.size : " + R.size());
            List<Restaurants> list = R.subList(Math.max(resturants.size() - 1, 0), R.size());
            this.resturants.addAll(list);
            notifyDataSetChanged();
            Log.e("TAG", "else: resturants.size : " + (resturants.size() - 1) + "R.size : " + R.size());
        }

    }

    public void hideAllExceptCollection() {
        for (int i = 0; i < resturants.size(); i++) {
            Restaurants restaurant = resturants.get(i);
            resturants.remove(restaurant);
        }
    }


    public void setDataForDetailCollectionActivity(Collection collection) {
        Log.e("TAG", "setDataForDetailCollectionActivity resturants.size : " + (resturants.size() - 1));
        hideAllExceptCollection();
        Log.e("TAG", "setDataForDetailCollectionActivity resturants.size : " + (resturants.size() - 1));
        detailCollectionForDetailCollectionActivity = collection;
        List<Restaurants> list = new ArrayList<>();
        Restaurants restaurants = new Restaurants();
        restaurants.setId(DetailColectionViewForDetailCollectionActivity);
        list.add(restaurants);
        resturants.addAll(list);
        notifyDataSetChanged();
        Log.e("TAG", "setDataForDetailCollectionActivity resturants.size : " + (resturants.size() - 1));
    }
}
